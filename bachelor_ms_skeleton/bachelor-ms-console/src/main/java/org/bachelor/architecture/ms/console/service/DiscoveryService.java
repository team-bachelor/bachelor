package org.bachelor.architecture.ms.console.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import org.apache.commons.lang3.StringUtils;
import org.bachelor.architecture.ms.console.cache.InMemoryServiceCache;
import org.bachelor.architecture.ms.console.utils.HttpUtil;
import org.bachelor.architecture.ms.console.vo.ServiceVo;
import org.bachelor.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.*;

/**
 * @描述: 注册与发现服务管理
 * @创建人: liuzhuo
 * @创建时间: 2018/12/9
 */
@Service
public class DiscoveryService {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryService.class);

    @Autowired
    private EurekaClientConfigBean eurekaConfigBean;

    @Autowired
    private DiscoveryClient eurekaClient;


    private InMemoryServiceCache serviceCache = new InMemoryServiceCache();

    public DiscoveryService() {
    }

    public Map<String, List<String>> getEurekaService() {
        Map<String, String> zonesMap = eurekaConfigBean.getAvailabilityZones();
        Set<String> zones = new HashSet<>(zonesMap.size());
        zonesMap.keySet().forEach(z -> {
            zones.add(z);
        });
        if (zones.size() == 0) {
            zones.add(EurekaClientConfigBean.DEFAULT_ZONE);
        }
        Map<String, List<String>> services = new LinkedHashMap<>(zones.size());
        zones.forEach(z -> {
            List<String> urls = eurekaConfigBean.getEurekaServerServiceUrls(z);
            services.put(z, urls);
        });
        return services;
    }

    public InstanceInfo getEurekaInstance(String url) {
        String response = HttpUtil.getRestTemplate().getForEntity(url + "status", String.class).getBody();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(response + "}");
            JsonNode info = node.findPath("instanceInfo");
            if (info == null) {
                return null;
            } else {
                return mapper.readValue(info.toString(), InstanceInfo.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<ServiceVo> getServices() {
        List<String> services = eurekaClient.getServices();
        services.forEach(id -> {
            ServiceVo vo = new ServiceVo();
            vo.setId(id);
            List<ServiceInstance> i = eurekaClient.getInstances(id);
            vo.setInstances(new ArrayList<>(i.size()));
            i.forEach(ins -> {
                if (ins instanceof EurekaDiscoveryClient.EurekaServiceInstance) {
                    EurekaDiscoveryClient.EurekaServiceInstance esi = ((EurekaDiscoveryClient.EurekaServiceInstance) ins);
                    //serviceCache.update(id, esi.getInstanceInfo());
                    vo.getInstances().add(esi.getInstanceInfo());
                }
            });
            //vos.add(vo);
            serviceCache.update(vo);
        });
        return serviceCache.getServices();
    }

    public ServiceVo getService(String serviceId) {
        if(StringUtils.isEmpty(serviceId)){
            return null;
        }
        Collection<ServiceVo> serviceVos = getServices();
        if (serviceVos == null || serviceVos.size() == 0) {
            return null;
        }
        ServiceVo s = null;
        for (ServiceVo st: serviceVos) {
            if(serviceId.equals(st.getId())){
                s = st;
            }
        }
        return s;
    }

    public void setServiceDown(String appID) {
        Collection<String> inIDs = getInstancesID(appID);
        inIDs.forEach(id -> {
            setInstanceOut(appID, id);
        });
    }

    public void setServiceUp(String serviceId) {
        Collection<String> inIDs = getInstancesID(serviceId);
        inIDs.forEach(id -> {
            setInstanceUp(serviceId, id);
        });
    }

    private Collection<String> getInstancesID(String serviceId) {
        return serviceCache.getInstancesId(serviceId);
    }

    private static String InstanceOut = "OUT_OF_SERVICE";
    private static String InstanceUp = "UP";

    public void setInstanceOut(String appID, String instanceID) {
        setInstanceStatus(appID, instanceID, InstanceOut);
        serviceCache.setInstanceOut(appID, instanceID);
    }

    public void setInstanceUp(String appID, String instanceID) {
        setInstanceStatus(appID, instanceID, InstanceUp);
        serviceCache.setInstanceUp(appID, instanceID);
    }

    private void setInstanceStatus(String appID, String instanceID, String status) {
        try {
            List<String> urls = eurekaConfigBean.getEurekaServerServiceUrls(eurekaConfigBean.getRegion());
            urls.forEach(url -> {
                HttpUtil.getRestTemplate().put(url + "apps/{1}/{2}/status?value=" + status,
                        HttpUtil.getJsonRequesBody(), appID, instanceID);
            });
        } catch (RestClientException e) {
            throw new BusinessException(e);
        }
    }
}
