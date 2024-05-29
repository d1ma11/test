package dto;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user", schema = "animals")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;

    private String username;
    private int age;
    private String password;

    @OneToMany(mappedBy = "id_user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = UsersRoles.class)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Integer id_user, String username, int age, String password, Set<Role> roles) {
        this.id_user = id_user;
        this.username = username;
        this.age = age;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role Role : this.getRoles()) {
            list.add(new SimpleGrantedAuthority(Role.getName().toString()));
        }
        return list;
    }

    public Integer getIdUser() {
        return id_user;
    }

    public void setIdUser(Integer id_user) {
        this.id_user = id_user;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
