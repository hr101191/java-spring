package com.hurui.model;

import com.hurui.entity.SpringConfigUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SpringConfigUserPrincipal implements UserDetails {

    private SpringConfigUser springConfigUser;

    public SpringConfigUserPrincipal() {
    }

    public SpringConfigUserPrincipal(SpringConfigUser springConfigUser) {
        this.springConfigUser = springConfigUser;
    }

    public SpringConfigUser getSpringConfigClientUser() {
        return springConfigUser;
    }

    public void setSpringConfigClientUser(SpringConfigUser springConfigUser) {
        this.springConfigUser = springConfigUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return this.springConfigUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.springConfigUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.springConfigUser.getEnabled();
    }
    
    public static SpringConfigUserPrincipalBuilder builder() {
        return new SpringConfigUserPrincipalBuilder();
    }

    public static class SpringConfigUserPrincipalBuilder {

        private SpringConfigUser springConfigUser;

        public SpringConfigUserPrincipalBuilder() {
        }

        public SpringConfigUserPrincipalBuilder setSpringConfigUser(SpringConfigUser springConfigUser) {
            this.springConfigUser = springConfigUser;
            return this;
        }

        public SpringConfigUserPrincipal build() {
            SpringConfigUserPrincipal springConfigUserPrincipal = new SpringConfigUserPrincipal();
            springConfigUserPrincipal.setSpringConfigClientUser(this.springConfigUser);
            return springConfigUserPrincipal;
        }
    }
}
