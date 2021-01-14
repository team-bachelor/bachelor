package cn.org.bachelor.up.oauth2.common.message.types;

/**
 * Created by team bachelor on 15/5/20.
 */
public enum ParameterStyle {
    BODY("body"),
    QUERY("query"),
    HEADER("header");

    private String parameterStyle;

    private ParameterStyle(String parameterStyle) {
        this.parameterStyle = parameterStyle;
    }

    public String toString() {
        return this.parameterStyle;
    }
}
