package com.edaoe.module.hotupdate;

import com.edaoe.App;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Catch
 * @date 2018-09-11 下午4:50
 * @description
 */
public class HotUpdateService {
	private static final int DEFAULT_NUM=3;
	private static Map<String,Integer> connMap=new HashMap<>();
	
	public void index(HttpServletResponse response, String key) {
		response.setContentType("text/event-stream");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			App.outMap.putIfAbsent(key,out);
			int num = DEFAULT_NUM;
			connMap.putIfAbsent(key, num);
			while (true) {
				out.println("data:keep-alive");
				out.println("event:message");
				out.println("id:" + key);
				out.println();
				out.flush();
				App.outMap.put(key, out);
				if (!connMap.containsKey(key)) {
					break;
				}
				num = connMap.get(key);
				if (num < 0) {
					break;
				}
				num--;
				connMap.put(key, num);
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		App.outMap.remove(key);
		connMap.remove(key);
	}
	
	public void holdConn(String key) {
		if (connMap.containsKey(key)){
			connMap.put(key, DEFAULT_NUM);
		}
	}
}
