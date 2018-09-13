package com.edaoe.module.site;


import cn.hutool.core.util.StrUtil;
import com.edaoe.App;
import com.edaoe.model.Site;
import com.edaoe.util.DateUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Catch
 * @date 2018-09-05 上午10:13
 * @description 本类主要提供静态方法，用以前端页面的调用
 */
public class SiteApi {
	
	/**
	 * 获取相对时间
	 *
	 * @param dateStr 日期字符串 yyyy-MM-dd HH:mm:ss
	 * @return 格式化后的日期
	 */
	public static String dateRelative(String dateStr) {
		return DateUtil.relative(DateUtil.parseDateTime(dateStr));
	}
	
	/**
	 * 获取静态资源路径前缀
	 *
	 * @param site 当前站点
	 * @return String 静态资源路径前缀
	 */
	public static String getAssetsPath(Site site) {
		return "/assets";
	}
	
	/**
	 * 获取文章内容
	 *
	 * @param site      当前站点
	 * @param articleId 文章id
	 * @return Article 文章对象
	 */
	public static Map getArticle(Site site, Long articleId) {
		return App.get(
				StrUtil.format("/devtools/getArticle?siteToken={}&articleId={}", App.siteToken, articleId),
				"article",
				Map.class);
	}
	
	/**
	 * 获取分类
	 *
	 * @param site 当前站点
	 * @return List<Category> 分类
	 */
	public static List getCategories(Site site) {
		return App.get(
				StrUtil.format("/devtools/getCategories?siteToken={}", App.siteToken),
				"categories",
				List.class);
	}
	
	/**
	 * 获取热门文章
	 */
	public static List getHotArticles(Site site) {
		return App.get(
				StrUtil.format("/devtools/getHotArticles?siteToken={}", App.siteToken),
				"articles",
				List.class);
	}
	
	public static List getHotArticles(Site site, int length) {
		return App.get(
				StrUtil.format("/devtools/getHotArticles?siteToken={}&length={}", App.siteToken, length),
				"articles",
				List.class);
	}
	
	/**
	 * 获取最近文章
	 */
	public static List getRecentArticles(Site site) {
		return App.get(
				StrUtil.format("/devtools/getRecentArticles?siteToken={}", App.siteToken),
				"articles",
				List.class);
	}
	
	public static List getRecentArticles(Site site, int length) {
		return App.get(
				StrUtil.format("/devtools/getRecentArticles?siteToken={}&length={}", App.siteToken, length),
				"articles",
				List.class);
	}
	
	/**
	 * 按照创建时间进行倒序文章排序
	 */
	public static Map getArticlesOfLatest(Site site) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfLatest?siteToken={}", App.siteToken),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfLatest(Site site, int pageNumber) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfLatest?siteToken={}&pageNumber={}", App.siteToken, pageNumber),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfLatest(Site site, int pageNumber, int pageSize) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfLatest?siteToken={}&pageNumber={}&pageSize={}", App.siteToken, pageNumber, pageSize),
				"page",
				Map.class);
	}
	
	/**
	 * 获取某个分类下的文章列表
	 */
	public static Map getArticlesOfCategory(Site site, String value) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfCategory?siteToken={}&value={}", App.siteToken, value),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfCategory(Site site, String value, int pageNumber) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfCategory?siteToken={}&value={}&pageNumber={}", App.siteToken, value, pageNumber),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfCategory(Site site, String value, int pageNumber, int pageSize) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfCategory?siteToken={}&value={}&pageNumber={}&pageSize={}", App.siteToken, value, pageNumber, pageSize),
				"page",
				Map.class);
	}
	
	/**
	 * 获取某个标签下的文章列表
	 */
	public static Map getArticlesOfTag(Site site, String value) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfTag?siteToken={}&value={}", App.siteToken, value),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfTag(Site site, String value, int pageNumber) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfTag?siteToken={}&value={}&pageNumber={}", App.siteToken, value, pageNumber),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfTag(Site site, String value, int pageNumber, int pageSize) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfTag?siteToken={}&value={}&pageNumber={}&pageSize={}", App.siteToken, value, pageNumber, pageSize),
				"page",
				Map.class);
	}
	
	/**
	 * 获取某个搜索词下的文章列表
	 */
	public static Map getArticlesOfSearch(Site site, String value) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfSearch?siteToken={}&value={}", App.siteToken, value),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfSearch(Site site, String value, int pageNumber) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfSearch?siteToken={}&value={}&pageNumber={}", App.siteToken, value, pageNumber),
				"page",
				Map.class);
	}
	
	public static Map getArticlesOfSearch(Site site, String value, int pageNumber, int pageSize) {
		return App.get(
				StrUtil.format("/devtools/getArticlesOfSearch?siteToken={}&value={}&pageNumber={}&pageSize={}", App.siteToken, value, pageNumber, pageSize),
				"page",
				Map.class);
	}
	
	public static List getTags(Site site) {
		return App.get(
				StrUtil.format("/devtools/getTags?siteToken={}", App.siteToken),
				"tags",
				List.class);
	}
	
}
