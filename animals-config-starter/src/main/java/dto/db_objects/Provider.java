package dto.db_objects;

public class Provider implements TableRecord{

    private Integer idProvider;
    private String name;
    private String phone;

    public Provider() {
    }

    public Provider(Integer idProvider, String name, String phone) {
        this.idProvider = idProvider;
        this.name = name;
        this.phone = phone;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
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

    @Override
    public String toString() {
        return "Provider{" +
                "idProvider=" + idProvider +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
