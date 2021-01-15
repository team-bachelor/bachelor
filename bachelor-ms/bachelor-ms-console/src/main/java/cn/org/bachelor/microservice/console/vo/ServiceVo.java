package cn.org.bachelor.microservice.console.vo;

import com.netflix.appinfo.InstanceInfo;

import java.util.List;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/12/23
 */
public class ServiceVo {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<InstanceInfo> getInstances() {
        return instances;
    }

    public void setInstances(List<InstanceInfo> instances) {
        this.instances = instances;
    }

    private List<InstanceInfo> instances;
}
