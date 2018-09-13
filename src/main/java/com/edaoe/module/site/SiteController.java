package com.edaoe.module.site;

import com.edaoe.interceptor.SiteInterceptor;
import com.edaoe.model.Site;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * @author Catch
 * @date 2018-06-11 14:06
 */
@Before(SiteInterceptor.class)
public class SiteController extends Controller {
	/**
	 * 用户首页
	 * @attr site site对象
	 * @attr key="index"
	 * @attr pageNumber
	 */
	public void index() {
		Site site = getAttr("site");
		setAttr("title", site.getName());
		setAttr("keywords", site.getName());
		setAttr("description", site.getDesc());
		render("index.html");
	}
	
	/**
	 * 用户分类页
	 * @attr key="category"
	 * @attr value=category.name
	 * @attr pageNumber
	 */
	public void category() {
		Site site = getAttr("site");
		setAttr("title", site.getName());
		setAttr("keywords", site.getName());
		setAttr("description", site.getDesc());
		render("category.html");
	}
	
	/**
	 * 用户标签页
	 * @attr key="tag"
	 * @attr value=tag.name
	 * @attr pageNumber
	 */
	public void tag() {
		Site site = getAttr("site");
		setAttr("title", site.getName());
		setAttr("keywords", site.getName());
		setAttr("description", site.getDesc());
		render("tag.html");
	}
	
	/**
	 * 用户搜索页
	 * @attr key="search"
	 * @attr value=search.words
	 * @attr pageNumber
	 */
	public void search() {
		Site site = getAttr("site");
		setAttr("title", site.getName());
		setAttr("keywords", site.getName());
		setAttr("description", site.getDesc());
		render("search.html");
	}
	
	/**
	 * 用户文章页
	 * @attr key="article"
	 * @attr value=article.id
	 */
	public void article() {
		Site site = getAttr("site");
		setAttr("title", site.getName());
		setAttr("keywords", site.getName());
		setAttr("description", site.getDesc());
		render("article.html");
	}
	
	/**
	 * 用户自定义页面
	 * @attr key="custom"
	 * @attr value=param
	 */
	public void custom() {
		Site site = getAttr("site");
		setAttr("title", site.getName());
		setAttr("keywords", site.getName());
		setAttr("description", site.getDesc());
		render("custom.html");
	}
	
}
