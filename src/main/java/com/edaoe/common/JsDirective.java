package com.edaoe.common;

import com.edaoe.util.AssetsUtil;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.stat.ParseException;
import com.jfinal.template.stat.Scope;

/**
 * @author Catch
 * @date 2018-09-13 上午9:57
 * @description
 */
public class JsDirective extends Directive {
	
	@Override
	public void exec(Env env, Scope scope, com.jfinal.template.io.Writer writer) {
		Object[] exprArray=this.exprList.evalExprList(scope);
		for (Object o : exprArray) {
			if (!(o instanceof String)) {
				throw new ParseException("参数必须为 String 类型", this.location);
			}
		}
		try {
			String path = AssetsUtil.getPath(exprArray,true);
			write(writer, path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
