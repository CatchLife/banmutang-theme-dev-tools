package com.edaoe.interceptor;

import cn.hutool.core.util.StrUtil;
import com.edaoe.App;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * @author Catch
 * @date 2018-09-11 下午1:35
 * @description
 */
public class SiteInterceptor implements Interceptor {
	
	@Override
	public void intercept(Invocation inv) {
		Controller c=inv.getController();
		String host=c.getAttrForStr("host");
		if (!host.equalsIgnoreCase(App.site.getHost())){
			c.redirect(StrUtil.EMPTY);
		}else{
			c.removeAttr("host");
			c.setAttr("site",App.site);
			c.setAttr("user",App.user);
			c.setAttr("isAuthor",true);
			inv.invoke();
		}
	}
}
