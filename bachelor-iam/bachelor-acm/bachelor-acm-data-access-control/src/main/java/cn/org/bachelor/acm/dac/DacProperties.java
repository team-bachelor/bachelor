package cn.org.bachelor.acm.dac;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(
        prefix = "bachelor.dac"
)
public class DacProperties extends Properties {
    private String a;
}
