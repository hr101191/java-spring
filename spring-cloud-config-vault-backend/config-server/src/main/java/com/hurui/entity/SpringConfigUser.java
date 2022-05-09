package com.hurui.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "spring_config_users")
@IdClass(value = SpringConfigUserId.class)
public class SpringConfigUser implements Serializable {

    private static final long serialVersionUID = 2471760682929231310L;

    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false, length = 60000)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    public SpringConfigUser() {
    }

    public SpringConfigUser(String username, String password, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public static SpringConfigUserBuilder builder() {
        return new SpringConfigUserBuilder();
    }

    public static class SpringConfigUserBuilder {

        private String username;

        private String password;

        private Boolean enabled;

        public SpringConfigUserBuilder() {
        }

        public SpringConfigUserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public SpringConfigUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public SpringConfigUserBuilder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public SpringConfigUser build() {
            SpringConfigUser springConfigUser = new SpringConfigUser();
            springConfigUser.setUsername(this.username);
            springConfigUser.setPassword(this.password);
            springConfigUser.setEnabled(this.enabled);
            return springConfigUser;
        }
    }
}
