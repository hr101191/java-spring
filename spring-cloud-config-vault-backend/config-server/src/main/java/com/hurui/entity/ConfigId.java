package com.hurui.entity;

import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@IdClass(ConfigId.class)
public class ConfigId implements Serializable {

    private static final long serialVersionUID = 6644466902179478217L;

    private String application;

    private String profile;

    private String label;

    private String key;

    public ConfigId() {

    }

    public ConfigId(String application, String profile, String label, String key) {
        this.application = application;
        this.profile = profile;
        this.label = label;
        this.key = key;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigId configId = (ConfigId) o;
        return label.equals(configId.label) && profile.equals(configId.profile) && key.equals(configId.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, profile, key);
    }
}
