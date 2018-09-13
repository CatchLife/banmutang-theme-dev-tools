package com.edaoe.module.index;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

/**
 * @author Catch
 * @date 2018-09-11 下午12:38
 * @description
 */
public class IndexController extends Controller {
	public void index(){
		redirect("/"+ PropKit.get("host"));
	}
}
