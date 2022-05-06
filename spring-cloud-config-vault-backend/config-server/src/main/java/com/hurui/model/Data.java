package com.hurui.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    private Map<String, String> data;

    @JsonAlias(value = {"meta_date"})
    private MetaData metaData;

    public Data() {
    }

    public Data(Map<String, String> data, MetaData metaData) {
        this.data = data;
        this.metaData = metaData;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
