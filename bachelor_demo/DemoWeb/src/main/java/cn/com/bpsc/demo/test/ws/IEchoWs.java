package org.bachelor.demo.test.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IEchoWs {

	public String echo(@WebParam(name = "msg") String msg);
}
