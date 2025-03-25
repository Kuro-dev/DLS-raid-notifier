package org.kurodev.config;

/**
 * @author kuro
 **/
public enum Setting {
    TOKEN("token", ""),
    ;

    private final String key;

    private final String defaultVal;

    Setting(String key, String defaultVal) {
        this.key = key;
        this.defaultVal = defaultVal;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultVal() {
        return defaultVal;
    }
}
