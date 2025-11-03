// 声明包名，表明该类所属的包路径
package cn.org.bachelor.web.context;

import cn.org.bachelor.context.IContext;
import cn.org.bachelor.web.util.RequestUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;

/**
 * ServletContextHolder 类实现了 ServletContextAware 和 ServletConfigAware 接口，
 * 用于持有 ServletContext 和 ServletConfig 信息，并将其设置到 RequestUtil 中。
 * 同时，它还可以将 ServletContext 的相关属性设置到 dataContext 中。
 */
@Service
public class ServletContextHolder implements ServletContextAware, ServletConfigAware {

    // 使用 @Resource 注解注入 IContext 类型的 dataContext 对象
    @Resource
    private IContext dataContext;

    /**
     * 实现 ServletConfigAware 接口的方法，当 ServletConfig 被设置时调用。
     * 将传入的 ServletConfig 设置到 RequestUtil 中。
     *
     * @param servletConfig 要设置的 ServletConfig 对象
     */
    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        // 调用 RequestUtil 的 setServletConfig 方法，将 servletConfig 传入
        RequestUtil.setServletConfig(servletConfig);
    }

    /**
     * 实现 ServletContextAware 接口的方法，当 ServletContext 被设置时调用。
     * 将传入的 ServletContext 设置到 RequestUtil 中，并将 ServletContext 的根路径设置到 dataContext 中。
     *
     * @param servletContext 要设置的 ServletContext 对象
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        // 调用 RequestUtil 的 setServletContext 方法，将 servletContext 传入
        RequestUtil.setServletContext(servletContext);
        // 注释掉的代码，可能是未完成或废弃的逻辑
        // vlService.setGloableAttribute(ContextConstant.WEB_CONTEXT_NAME, servletContext.get);
        // 将 ServletContext 的根路径设置到 dataContext 中，键为 WebContext.WEB_DOC_ROOT
        dataContext.setApplicationAttribute(WebContext.WEB_DOC_ROOT, servletContext.getRealPath("/"));
    }
}