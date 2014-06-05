package org.bachelor.demo.test.ws;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EchoWsClientTest {

	public static void main(String[] args) {
		// 加载客户端的配置定义  
		ApplicationContext context = new ClassPathXmlApplicationContext("*-client.xml");  
		// 获取定义的 Web Service Bean  
		IEchoWs helloService = (IEchoWs) context.getBean("echoServiceClient");  
		String username = "CXF";  
		// 调用方法进行服务消费  
		String result = helloService.echo(username);
		System.out.println("Result:" + result);  
	}
}
