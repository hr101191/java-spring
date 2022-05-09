package com.hurui.entity;

import javax.persistence.IdClass;
import java.io.Serializable;

@IdClass(SpringConfigUserId.class)
public class SpringConfigUserId implements Serializable {

    private static final long serialVersionUID = 3376398912539307401L;

    private String username;

    public SpringConfigUserId() {
    }

    public SpringConfigUserId(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static SpringConfigUserIdBuilder builder() {
        return new SpringConfigUserIdBuilder();
    }

    public static class SpringConfigUserIdBuilder {

        private String username;

        public SpringConfigUserIdBuilder() {

        }

        public SpringConfigUserIdBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public SpringConfigUserId build() {
            SpringConfigUserId springConfigUserId = new SpringConfigUserId();
            springConfigUserId.setUsername(this.username);
            return springConfigUserId;
        }
    }
}
