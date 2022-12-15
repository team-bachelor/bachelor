package cn.org.bachelor.acm.dac;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bachelor.dac", ignoreInvalidFields = true)
public class DacConfiguration {

    private boolean enabled = true;

    private DacFieldConfig[] fields;

    private String[] packages;

    public DacFieldConfig[] getFields() {
        return fields;
    }

    public void setFields(DacFieldConfig[] fields) {
        this.fields = fields;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getPackages() {
        return packages;
    }

    public void setPackages(String[] packages) {
        this.packages = packages;
    }
}
