package cn.org.bachelor.acm.dac;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bachelor.dac", ignoreInvalidFields = true)
public class DacConfiguration {

    private boolean enabled = false;

    private DacField[] fields;

    public DacField[] getFields() {
        return fields;
    }

    public void setFields(DacField[] fields) {
        this.fields = fields;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
