/**
 * Description:  修改DATE_FORMAT格式，去掉默认的中间加T格式
 * @Author Alexhendar
 * @Date: Created in 2018/10/30 9:28
 * @Modified By:
 */
package com.netflix.eureka.resources;

import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.util.StatusInfo;
import com.netflix.eureka.util.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/{version}/status")
@Produces({"application/xml", "application/json"})
public class StatusResource {
    private static final Logger logger = LoggerFactory.getLogger(StatusResource.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    private final StatusUtil statusUtil;

    @Inject
    StatusResource(EurekaServerContext server) {
        this.statusUtil = new StatusUtil(server);
    }

    public StatusResource() {
        this(EurekaServerContextHolder.getInstance().getServerContext());
    }

    @GET
    public StatusInfo getStatusInfo() {
        return statusUtil.getStatusInfo();
    }

    public static String getCurrentTimeAsString() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(new Date());
    }
}
