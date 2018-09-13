package com.edaoe.module.hotupdate;

import com.edaoe.aop.Inject;
import com.jfinal.core.Controller;

/**
 * @author Catch
 * @date 2018-09-11 下午4:43
 * @description
 */
public class HotUpdateController extends Controller {
	@Inject
	private HotUpdateService service;
	
	public void index(){
		String key=getPara("key");
		service.index(getResponse(),key);
		renderNull();
	}
	
	public void holdConn(){
		String key=getPara("key");
		service.holdConn(key);
		renderNull();
	}
}
