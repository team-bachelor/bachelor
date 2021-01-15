package cn.org.bachelor.web.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import cn.org.bachelor.web.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;


import cn.org.bachelor.context.IVLService;

@Service
public class ServletContextHolder implements ServletContextAware,ServletConfigAware{

	@Autowired
	private IVLService vlService;
	
	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		RequestUtils.setServletConfig(servletConfig);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		RequestUtils.setServletContext(servletContext);
		//vlService.setGloableAttribute(ContextConstant.WEB_CONTEXT_NAME, servletContext.get);
		vlService.setGloableAttribute(ContextConstant.WEB_DOC_ROOT,servletContext.getRealPath("/"));
	}

}
