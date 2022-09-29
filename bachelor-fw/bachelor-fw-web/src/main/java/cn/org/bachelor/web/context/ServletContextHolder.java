package cn.org.bachelor.web.context;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import cn.org.bachelor.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;


import cn.org.bachelor.context.IContext;

@Service
public class ServletContextHolder implements ServletContextAware,ServletConfigAware{

	@Resource
	private IContext dataContext;
	
	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		RequestUtil.setServletConfig(servletConfig);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		RequestUtil.setServletContext(servletContext);
		//vlService.setGloableAttribute(ContextConstant.WEB_CONTEXT_NAME, servletContext.get);
		dataContext.setApplicationAttribute(WebContext.WEB_DOC_ROOT,servletContext.getRealPath("/"));
	}

}
