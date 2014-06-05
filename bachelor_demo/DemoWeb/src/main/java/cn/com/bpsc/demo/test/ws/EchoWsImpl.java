package org.bachelor.demo.test.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import org.bachelor.demo.test.biz.ITestWsBiz;
import org.bachelor.context.service.IVLService;

@WebService(endpointInterface="org.bachelor.demo.test.ws.IEchoWs")
public class EchoWsImpl implements IEchoWs{

	@Autowired
	private IVLService vlService;
	
	@Autowired
	private ITestWsBiz testBiz;
	
	@Override
	public String echo(String msg) {
		System.out.println("hello:" + msg);
		
		vlService.setRequestAttribute("MSG", msg);
		
		String msg2 = testBiz.getMsg();
		System.out.println(msg2);
		return "hello:" + msg;
	}

}
