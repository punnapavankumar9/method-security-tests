package org.punna.methodsecurity.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class User extends org.springframework.security.core.userdetails.User {
    private String JobLevel;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities, String JobLevel) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.JobLevel = JobLevel;
    }

    public static class UserBuilder {
        String username;
        String password;
        List<String> roles = new ArrayList<>();
        String JobLevel;

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder JobLevel(String JobLevel) {
            this.JobLevel = JobLevel;
            return this;
        }

        public User build() {
            return new User(username,
                    password,
                    true,
                    true,
                    true,
                    true,
                    roles
                            .stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .toList(),
                    JobLevel);
        }
    }
}
