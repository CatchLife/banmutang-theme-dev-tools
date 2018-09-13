package com.edaoe.watch;

import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import com.edaoe.App;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Map;

/**
 * @author Catch
 * @date 2018-09-11 下午5:21
 * @description
 */
public class Watch {
	public static void watch() {
		File file = new File("source");
		WatchMonitor watchMonitor = WatchMonitor.create(file);
		
		watchMonitor.setWatcher(new Watcher() {
			@Override
			public void onCreate(WatchEvent<?> event, Path currentPath) {
				update();
			}
			
			@Override
			public void onModify(WatchEvent<?> event, Path currentPath) {
				update();
			}
			
			@Override
			public void onDelete(WatchEvent<?> event, Path currentPath) {
				update();
			}
			
			@Override
			public void onOverflow(WatchEvent<?> event, Path currentPath) {
				update();
			}
		});
		
		//设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
		watchMonitor.setMaxDepth(3);
		//启动监听
		watchMonitor.start();
	}
	
	private static void update(){
		for (Map.Entry<String, PrintWriter> entry : App.outMap.entrySet()) {
			PrintWriter out =entry.getValue();
			out.println("data:reload");
			out.println("event:reload");
			out.println("id:" + entry.getKey());
			out.println();
			out.flush();
		}
	}
}
