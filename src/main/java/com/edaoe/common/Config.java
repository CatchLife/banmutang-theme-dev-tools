package com.edaoe.common;

import cn.hutool.core.util.StrUtil;
import com.edaoe.aop.AopControllerFactory;
import com.edaoe.handler.SiteHandler;
import com.edaoe.handler.StaticHandler;
import com.edaoe.module.hotupdate.HotUpdateController;
import com.edaoe.module.index.IndexController;
import com.edaoe.module.site.SiteApi;
import com.edaoe.module.site.SiteController;
import com.edaoe.scan.Scan;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Catch
 * @date 2018-09-11 上午11:52
 * @description
 */
public class Config extends JFinalConfig {
	
	public static List<String> actionKeys = new ArrayList<>();
	
	@Override
	public void configConstant(Constants me) {
//		me.setDevMode(true);
		me.setControllerFactory(new AopControllerFactory());
//		me.setJsonFactory(MixedJsonFactory.me());
	}
	
	@Override
	public void configRoute(Routes me) {
//		me.setBaseViewPath("/_view");
		me.add("/", IndexController.class);
		me.add("/site", SiteController.class, "/page");
		me.add("/hotUpdate", HotUpdateController.class);
	}
	
	/**
	 * 配置模板引擎，通常情况只需配置共享的模板函数
	 */
	@Override
	public void configEngine(Engine me) {
		me.setDevMode(true)
				.setBaseTemplatePath("./source")
				.addSharedFunction(new ClassPathSource("__layout.html"))
				.addSharedMethod(SiteApi.class)
				.addSharedObject("dev", true)
				.addDirective("js", JsDirective.class);
	}
	
	@Override
	public void configPlugin(Plugins me) {
	}
	
	@Override
	public void configInterceptor(Interceptors me) {
	}
	
	@Override
	public void configHandler(Handlers me) {
		me.add(new StaticHandler());
		me.add(new SiteHandler());
	}
	
	@Override
	public void afterJFinalStart() {
		// 让 UserHandler 放行一部分已有的 Router
		List<String> aks = JFinal.me().getAllActionKeys();
		for (String ak : aks) {
			if (StrUtil.EMPTY.equals(ak) || StrUtil.SLASH.equals(ak)) {
				continue;
			}
			if (ak.indexOf(StrUtil.SLASH) == ak.lastIndexOf(StrUtil.SLASH)) {
				actionKeys.add(ak);
			}
		}
		System.out.println("服务已启动，您可以访问 http://localhost:" + PropKit.getInt("port", 8080) + " 实时预览。");
		new Thread(Scan::scan).start();
	}
}
