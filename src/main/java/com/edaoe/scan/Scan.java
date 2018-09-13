package com.edaoe.scan;

import com.edaoe.App;
import com.edaoe.build.Builder;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Catch
 * @date 2018-09-11 下午5:55
 * @description
 */
public class Scan {
	
	private static void help() {
		System.out.println("请不要关闭本窗口！程序会持续监听文件改动实时渲染。");
		System.out.println("您可以输入以下命令，进行相关操作：");
		System.out.println("help         查看帮助");
		System.out.println("exit         退出程序");
		System.out.println("reload       缓存模式下数据重新加载");
		System.out.println("cache on     启用缓存（页面加载速度更快，但数据变更需要手动reload）");
		System.out.println("cache off    关闭缓存（数据实时展示）");
		System.out.println("build        打包");
	
	}
	
	/**
	 * 维持程序运行
	 */
	public static void scan() {
		help();
		
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			String in = scan.nextLine().toLowerCase();
			switch (in) {
				case "":
					break;
				case "help":
					help();
					break;
				case "exit":
					System.out.println("您成功已退出！");
					System.exit(0);
					break;
				case "reload":
					App.cacheMap = new HashMap<>();
					System.out.println("重新加载数据完成");
					break;
				case "cache on":
					App.cache = true;
					System.out.println("缓存已开启");
					break;
				case "cache off":
					App.cache = false;
					App.cacheMap = new HashMap<>();
					System.out.println("缓存已关闭");
					break;
				case "build":
					Builder.build();
					break;
				default:
					help();
			}
		}
	}
}
