package com.example.dictionary;

public class Dict {
    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public Dict() {
    }

    public Dict(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;


    }
}
