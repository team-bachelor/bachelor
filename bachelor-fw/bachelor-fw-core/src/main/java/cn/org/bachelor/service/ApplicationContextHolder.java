package cn.org.bachelor.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public ApplicationContextHolder() {
    }

    @Override
    public void setApplicationContext(ApplicationContext ac)
            throws BeansException {
        applicationContext = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     *  得到本机IP
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static String getServerIp() throws Exception{
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        String ipAddr = "";
        while(allNetInterfaces.hasMoreElements()){
            NetworkInterface netInterface = (NetworkInterface)allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            boolean flag = false;
            while(addresses.hasMoreElements()){
                ip = (InetAddress)addresses.nextElement();
                if(ip!=null && ip instanceof Inet4Address && !"127.0.0.1".equals(ip.getHostAddress())){
                    ipAddr = (ip.getHostAddress());
                    flag = true;
                    break;
                }
            }
            if(flag == true){
                break;
            }
        }
        /** Window 下面直接引用就行 **/
        //InetAddress.getLocalHost().getHostAddress()
        return ipAddr;
    }
}
