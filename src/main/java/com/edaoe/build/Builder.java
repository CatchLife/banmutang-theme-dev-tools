package com.edaoe.build;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.jfinal.template.source.ClassPathSource;

import java.io.File;

/**
 * @author Catch
 * @date 2018-09-12 下午2:52
 * @description
 */
public class Builder {
	private static final String[] VIEWS={"index","article","category","search","tag"};
	
	public static void init(){
		File configFile=new File("config.txt");
		if (!configFile.exists()){
			String configTemplate=new ClassPathSource("configTemplate.txt").getContent().toString();
			FileUtil.touch(configFile);
			FileUtil.writeUtf8String(configTemplate,configFile);
		}
		
		File sourceFile=new File("source");
		if (!sourceFile.exists()){
			sourceFile.mkdir();
			new File("source/assets").mkdir();
			new File("source/page").mkdir();
			String viewTemplate=new ClassPathSource("viewTemplate.txt").getContent().toString();
			for (String view : VIEWS) {
				File viewFile=new File("source/page/".concat(view).concat(".html"));
				FileUtil.touch(viewFile);
				FileUtil.writeUtf8String(StrUtil.format(viewTemplate,view),viewFile);
			}
		}
	}
	
	public static void build(){
		System.out.println("\n开始打包...");
		// 1. 校验文件结构
		if (check()){
			// 2. 查找合并js、css，并进行压缩，替换模板文件
			if (compile()){
				// 3. 打包整理，清除临时文件
				File zipFile=zip();
				System.out.println("打包完成！\n输出目录："+zipFile.getAbsolutePath());
			}
		}
	}
	
	private static boolean check(){
		System.out.println("检查文件结构...");
		File indexFile= new File("source/page/index.html");
		if (!indexFile.exists()){
			System.out.println("请检查文件结构，未找到 "+indexFile.getAbsolutePath());
			return false;
		}
		File screenshotFile=new File("source/assets/screenshot.png");
		if (!screenshotFile.exists()){
			System.out.println("请检查文件结构，未找到 "+screenshotFile.getAbsolutePath());
			return false;
		}
		System.out.println("文件结构正确！");
		return true;
	}
	
	/**
	 * todo 压缩合并js,css
	 * @return
	 */
	private static boolean compile(){
		return true;
	}
	
	/**
	 * @return 打包后的文件
	 */
	private static File zip(){
		File destDir=new File("releases");
		if (!destDir.exists()){
			destDir.mkdirs();
		}
		File zipFile=new File("releases/"+ System.currentTimeMillis() +".zip");
		File sourceFile=new File("source/");
		return ZipUtil.zip(zipFile,false,sourceFile);
	}
}
