package cn.org.bachelor.iam.dac.client;

public class DacFieldConfig {

    public DacFieldConfig(){
    }
    public DacFieldConfig(String name){
        this.name = name;
    }

    private boolean enable = true;
    private String name;
    private boolean deep = false;
    private String pattern = "00000000";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isDeep() {
        return deep;
    }

    public void setDeep(boolean deep) {
        this.deep = deep;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
