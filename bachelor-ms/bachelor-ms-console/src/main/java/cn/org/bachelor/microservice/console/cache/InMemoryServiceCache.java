package cn.org.bachelor.microservice.console.cache;

import cn.org.bachelor.microservice.console.vo.ServiceVo;
import com.netflix.appinfo.InstanceInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/1/15
 */
public class InMemoryServiceCache {
    public static final String CONFIGURATION_PROPERTY_NAME = "InMemoryServiceCache";
    private static Map<String, Map<String, InstanceInfo>> instanceCache = new HashMap<>();
    private static Map<String, ServiceVo> serviceCache = new HashMap<>();

    public void update(ServiceVo service) {
        service.getInstances().forEach(i -> {
            update(service.getId(), i);
        });
        service.getInstances().clear();
        if (serviceCache.containsKey(service.getId())) {
            serviceCache.replace(service.getId(), service);
        } else {
            serviceCache.put(service.getId(), service);
        }
    }

    private void update(String serviceId, InstanceInfo info) {
        Map<String, InstanceInfo> sm = instanceCache.get(serviceId);
        if (sm == null) {
            sm = new HashMap<>();
            instanceCache.put(serviceId, sm);
        }
        if (sm.containsKey(info.getId())) {
            sm.replace(info.getId(), info);
        } else {
            sm.put(info.getId(), info);
        }

    }

    public void setInstanceOut(String appID, String instanceID) {
        if (instanceCache.containsKey(appID)) {
            InstanceInfo info = instanceCache.get(appID).get(instanceID);
            if (info != null) {
                info.setStatus(InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
            }
        }
    }

    public void setInstanceUp(String appID, String instanceID) {
        if (instanceCache.containsKey(appID)) {
            InstanceInfo info = instanceCache.get(appID).get(instanceID);
            if (info != null) {
                info.setStatus(InstanceInfo.InstanceStatus.UP);
            }
        }
    }

    public Collection<ServiceVo> getServices() {
        Collection<ServiceVo> services = serviceCache.values();
        for (ServiceVo s : services) {
            s.getInstances().clear();
            s.getInstances().addAll(getInstances(s.getId()));
        }
        return services;
    }

    public ServiceVo getService(String serviceId) {
        ServiceVo service = serviceCache.get(serviceId);
        if (service == null) return null;
        service.getInstances().clear();
        service.getInstances().addAll(getInstances(service.getId()));
        return service;
    }
    public Collection<String> getInstancesId(String serviceId){
        Map<String, InstanceInfo> im = instanceCache.get(serviceId);
        return im == null ? Collections.emptySet() : im.keySet();
    }
    public Collection<InstanceInfo> getInstances(String serviceId) {
        Map<String, InstanceInfo> im = instanceCache.get(serviceId);
        return im == null ? Collections.emptyList() : im.values();
    }
}
