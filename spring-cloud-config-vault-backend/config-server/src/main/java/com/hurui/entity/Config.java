package com.hurui.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "configs")
@IdClass(value = ConfigId.class)
public class Config implements Serializable {

    private static final long serialVersionUID = -5951273951160038553L;

    @Id
    @Column(name = "application", nullable = false)
    private String application;

    @Id
    @Column(name = "profile", nullable = false)
    private String profile;

    @Id
    @Column(name = "label", nullable = false)
    private String label;

    @Id
    @Column(name = "key", nullable = false, length = 1000)
    private String key;

    @Column(name = "value", length = 60000)
    private String value;

    public Config() {

    }

    public Config(String application, String profile, String label, String key, String value) {
        this.application = application;
        this.profile = profile;
        this.label = label;
        this.key = key;
        this.value = value;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    public static class ConfigBuilder {

        private String application;

        private String profile;

        private String label;

        private String key;

        private String value;

        public ConfigBuilder() {
        }

        public ConfigBuilder setApplication(String application) {
            this.application = application;
            return this;
        }

        public ConfigBuilder setProfile(String profile) {
            this.profile = profile;
            return this;
        }

        public ConfigBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public ConfigBuilder setKey(String key) {
            this.key = key;
            return this;
        }

        public ConfigBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public Config build() {
            Config config = new Config();
            config.setApplication(this.application);
            config.setProfile(this.profile);
            config.setLabel(this.label);
            config.setKey(this.key);
            config.setValue(this.value);
            return config;
        }
    }
}
