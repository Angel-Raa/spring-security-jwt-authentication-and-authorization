package com.github.angel.raa.modules.persistence.models.auth;



import com.github.angel.raa.modules.utils.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class Users implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", length = 50, unique = true)
    private String username;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return null;
        if (role.getPermissions() == null) return null;

        return role.getPermissions().stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        /*
          return role.getPermissions().stream()
                .map(rol -> {
                    String permission = rol.name();
                    return new SimpleGrantedAuthority(permission);
                }).collect(Collectors.toList());
         */

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
        return true;
    }
}
