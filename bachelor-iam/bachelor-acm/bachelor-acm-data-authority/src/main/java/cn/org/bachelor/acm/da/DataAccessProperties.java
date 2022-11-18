package cn.org.bachelor.acm.da;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(
        prefix = "bachelor.da"
)
public class DataAccessProperties extends Properties {
}
