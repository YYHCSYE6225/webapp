package edu.neu.coe.csye6225.webapp.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
public class UserEntity implements UserDetails {
    private String Username;
    private String password;
    private Boolean enabled=true;
    private int is_active;
    private int role;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean accountNonExpired=true;
    private Boolean accountNonLocked=true;
    private Boolean credentialsNonExpired=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return Username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
