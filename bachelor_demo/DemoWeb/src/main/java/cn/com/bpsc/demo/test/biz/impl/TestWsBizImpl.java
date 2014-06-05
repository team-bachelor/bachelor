package org.bachelor.demo.test.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.demo.test.biz.ITestWsBiz;
import org.bachelor.context.service.IVLService;

@Service
public class TestWsBizImpl implements ITestWsBiz {

	@Autowired
	private IVLService vlService;
	
	@Override
	public String getMsg() {
		String msg = (String)vlService.getRequestAttribute("MSG");
		return msg;
	}

}
