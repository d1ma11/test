package dto.db_objects;

import jakarta.persistence.*;

@Entity
@Table(name = "provider", schema = "animals")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProvider;
    private String name;
    private String phone;

    public Provider() {
    }

    public Provider(int idProvider, String name, String phone) {
        this.idProvider = idProvider;
        this.name = name;
        this.phone = phone;
    }

    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
