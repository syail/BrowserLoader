package com.brady.browser;

public class LocalStorageItem {
    public String key;
    public String value;

    public LocalStorageItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return key + " = " + value;
    }
}
