package technikal.task.fishmarket.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shop_user")
public class ShopUser implements UserDetails {

    @Id
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private ShopUserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    public String getUsername() {
        return username;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ShopUserRole getRole() {
        return role;
    }

    public void setRole(ShopUserRole role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof ShopUser shopUser) {
            return Objects.equals(username, shopUser.username);
        } else {
            return false;
        }
    }
}
