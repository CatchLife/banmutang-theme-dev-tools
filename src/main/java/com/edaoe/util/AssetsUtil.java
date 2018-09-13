package com.edaoe.util;

import com.jfinal.log.Log;

import java.nio.charset.Charset;

/**
 * @author Catch
 * @date 2018-09-13 上午10:55
 * @description
 */
public class AssetsUtil {
	private static final Log log = Log.getLog(AssetsUtil.class);
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final String JS_EXT = ".js", CSS_EXT = ".css";
	private static final String PROTOCOL = "^https?://.+$";
	
	public static String getPath(Object[] obj,boolean isJs){
		return "";
	}
	
}
