package com.edaoe.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Catch
 * @date 2018-09-11 下午4:09
 * @description
 */
public class StaticHandler extends Handler {
	private static final String STATIC_FILE_PREFIX = "/assets";
	
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		if (target.startsWith(STATIC_FILE_PREFIX)) {
			Path path = Paths.get("source" + target);
			FileInputStream fis = null;
			OutputStream os = null;
			try {
				response.setContentType(Files.probeContentType(path));
				fis = new FileInputStream(path.toString());
				os = new BufferedOutputStream(response.getOutputStream());
				byte[] buf = new byte[512];
				int len;
				while ((len = fis.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				os.flush();
				isHandled[0] = true;
				return;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		next.handle(target, request, response, isHandled);
	}
}
