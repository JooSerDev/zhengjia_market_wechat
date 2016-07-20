package com.joosure.server.mvc.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joosure.server.mvc.wechat.dispatcher.MyNativeDispatcher;
import com.shawn.server.core.http.ResponseHandler;

@Controller
@RequestMapping("/wechat")
public class WechatNativeController {

	@RequestMapping("/native")
	public void nativeInterface(HttpServletRequest request, HttpServletResponse response, Model model) {
		MyNativeDispatcher myDispatcher = new MyNativeDispatcher(request);
		String result = myDispatcher.execute();
		System.out.println(result);
		ResponseHandler.output(response, result);
	}

}
