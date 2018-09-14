package com.edaoe;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.dialect.console.ConsoleLog;
import cn.hutool.log.level.Level;
import com.edaoe.build.Builder;
import com.edaoe.model.Site;
import com.edaoe.model.User;
import com.edaoe.watch.Watch;
import com.jfinal.core.JFinal;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Catch
 * @date 2018-09-11 上午11:50
 * @description
 */
@SuppressWarnings("unchecked")
public class App {
	public static Site site;
	public static String siteToken;
	public static User user;
	public static Map<String, PrintWriter> outMap = new HashMap<>();
	public static Map<String, Object> cacheMap = new HashMap<>();
	public static boolean cache;
	
	public static void main(String[] args) {
		ConsoleLog.setLevel(Level.ERROR);
		Builder.init();
		PropKit.use(new File("config.txt"))
				.appendIfExists(new File("config.dev.txt"));
		cache=PropKit.getBoolean("cache",true);
		verifyConfig();
		initSiteToken();
		initSite();
		initUser();
		Watch.watch();
		startJFinal();
	}
	
	private static void startJFinal(){
		if (PropKit.getBoolean("dev",false)){
			JFinal.start("src/main/resources", PropKit.getInt("port", 8080), "/");
		}else{
			File file=new File("classPath");
			if (file.exists()){
				JFinal.start(file.getAbsolutePath(), 8080, "/");
			}else{
				String basePath = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String classPath = basePath.substring(0, basePath.lastIndexOf("/")) + "/class-path";
				ZipUtil.unzip(basePath, classPath);
				PathKit.setWebRootPath(classPath);
				PathKit.setRootClassPath(classPath);
				JFinal.start(classPath, 8080, "/");
			}
		}
	}
	
	private static final String ERROR = "错误：errno:{},errmsg:{}";
	
	public static void error(JSONObject obj) {
		String message = StrUtil.format(ERROR, obj.getInt("errno"), obj.getStr("errmsg"));
		LogKit.error(message);
	}
	
	private static void verifyConfig() {
		if (StrKit.isBlank(PropKit.get("mobile"))) {
			System.out.println("手机号不能为空！请检查您的配置文件。");
			System.exit(-1);
		}
		if (StrKit.isBlank(PropKit.get("password"))) {
			System.out.println("密码不能为空！请检查您的配置文件。");
			System.exit(-1);
		}
		if (StrKit.isBlank(PropKit.get("host"))) {
			System.out.println("专属域不能为空！请检查您的配置文件。");
			System.exit(-1);
		}
	}
	
	private static void initSiteToken() {
		siteToken = get("/devtools/getSiteToken?mobile="
				+ PropKit.get("mobile")
				+ "&password="
				+ PropKit.get("password")
				+ "&host="
				+ PropKit.get("host"), "siteToken", String.class);
	}
	
	
	private static void initSite() {
		Map map = get("/devtools/getSite?siteToken=" + siteToken, "site", Map.class);
		site = new Site().put(map);
	}
	
	private static void initUser() {
		Map map = get("/devtools/getUser?siteToken=" + siteToken, "user", Map.class);
		user = new User().put(map);
	}
	
	public static <T> T get(String otherUrl, String key, Class<T> type) {
		if (cache && cacheMap.containsKey(otherUrl)) {
			return (T) cacheMap.get(otherUrl);
		}
		String body = HttpUtil.get(PropKit.get("baseUrl", "http://edaoe.com") + otherUrl);
		JSONObject obj = JSONUtil.parseObj(body);
		if (!obj.getBool("ok")) {
			App.error(obj);
			System.exit(-1);
		}
		T t = obj.get(key, type);
		if (cache) {
			cacheMap.put(otherUrl, t);
		}
		return t;
	}
	
}
