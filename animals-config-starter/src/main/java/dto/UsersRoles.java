package dto;

import jakarta.persistence.*;

@Entity
@Table(name = "users_roles", schema = "animals")
public class UsersRoles {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User id_user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    private Role id_role;

    public UsersRoles() {
    }

    public UsersRoles(User id_user, Role id_role) {
        this.id_user = id_user;
        this.id_role = id_role;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Role getId_role() {
        return id_role;
    }

    public void setId_role(Role id_role) {
        this.id_role = id_role;
    }
}
