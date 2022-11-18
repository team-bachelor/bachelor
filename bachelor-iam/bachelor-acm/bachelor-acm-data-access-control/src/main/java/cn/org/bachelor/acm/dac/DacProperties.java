package cn.org.bachelor.acm.dac;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(
        prefix = "bachelor.da"
)
public class DacProperties extends Properties {
}
