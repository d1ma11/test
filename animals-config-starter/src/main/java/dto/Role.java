package dto;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role", schema = "animals")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_role;

    @Enumerated(EnumType.STRING)
    private RoleNameEnum roleName;

    @OneToMany(mappedBy = "id_role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = UsersRoles.class)
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(RoleNameEnum roleName) {
        this.roleName = roleName;
    }

    public Role(Integer id_role, RoleNameEnum roleName, Set<User> users) {
        this.id_role = id_role;
        this.roleName = roleName;
        this.users = users;
    }

    public Integer getIdRole() {
        return id_role;
    }

    public void setIdRole(Integer id_role) {
        this.id_role = id_role;
    }

    public RoleNameEnum getName() {
        return roleName;
    }

    public void setName(RoleNameEnum name) {
        this.roleName = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName().name();
    }
}