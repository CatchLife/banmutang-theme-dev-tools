package com.edaoe.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.edaoe.common.Config;
import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * @author Catch
 * @date 2018-04-25 18:13
 */
public class SiteHandler extends Handler {
	
	private static final Pattern INDEX_PATTERN = Pattern.compile("^/[0-9a-zA-Z_]+/?$");
	private static final Pattern PATTERN = Pattern.compile("^/[0-9a-zA-Z_]+/(page|article|category|search|tag|custom)/[^/]+((/)|(/page/[0-9]+))?$");
	
	private static final String SITE_INDEX_TARGET = "/site/index";
	private static final String SITE_CATEGORY_TARGET = "/site/category";
	private static final String SITE_TAG_TARGET = "/site/tag";
	private static final String SITE_SEARCH_TARGET = "/site/search";
	private static final String SITE_ARTICLE_TARGET = "/site/article";
	private static final String SITE_CUSTOM_TARGET = "/site/custom";
	
	private static final String INDEX = "index";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String HOST = "host";
	private static final String CATEGORY = "category";
	private static final String TAG = "tag";
	private static final String PAGE = "page";
	private static final String ARTICLE = "article";
	private static final String SEARCH = "search";
	private static final String CUSTOM = "custom";
	
	private static final String KEY = "key";
	private static final String VALUE = "value";
	
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		boolean isSkip = false;
		for (String ak : Config.actionKeys) {
			if (target.startsWith(ak)) {
				isSkip = true;
				break;
			}
		}
		if (!isSkip) {
			String[] para = target.split(StrUtil.SLASH);
			if (INDEX_PATTERN.matcher(target).matches()) {
				target = SITE_INDEX_TARGET;
				request.setAttribute(KEY, INDEX);
				request.setAttribute(HOST, para[1]);
				request.setAttribute(PAGE_NUMBER, 1);
			} else if (PATTERN.matcher(target).matches()) {
				request.setAttribute(HOST, para[1]);
				String type = para[2];
				switch (type) {
					case PAGE:
						target = SITE_INDEX_TARGET;
						request.setAttribute(KEY, INDEX);
						request.setAttribute(PAGE_NUMBER, Integer.parseInt(para[3]));
						break;
					case ARTICLE:
						target = SITE_ARTICLE_TARGET;
						request.setAttribute(KEY, ARTICLE);
						request.setAttribute(VALUE, Long.parseLong(para[3]));
						break;
					case CATEGORY:
						target = SITE_CATEGORY_TARGET;
						request.setAttribute(KEY, CATEGORY);
						request.setAttribute(VALUE, URLUtil.decode(para[3]));
						request.setAttribute(PAGE_NUMBER, para.length == 4 ? 1 : Integer.parseInt(para[5]));
						break;
					case SEARCH:
						target = SITE_SEARCH_TARGET;
						request.setAttribute(KEY, SEARCH);
						request.setAttribute(VALUE, URLUtil.decode(para[3]));
						request.setAttribute(PAGE_NUMBER, para.length == 4 ? 1 : Integer.parseInt(para[5]));
						break;
					case TAG:
						target = SITE_TAG_TARGET;
						request.setAttribute(KEY, TAG);
						request.setAttribute(VALUE, URLUtil.decode(para[3]));
						request.setAttribute(PAGE_NUMBER, para.length == 4 ? 1 : Integer.parseInt(para[5]));
						break;
					case CUSTOM:
						target = SITE_CUSTOM_TARGET;
						request.setAttribute(KEY, CUSTOM);
						request.setAttribute(VALUE, URLUtil.decode(para[3]));
						break;
					default:
				}
			}
		}
		next.handle(target, request, response, isHandled);
	}
}
