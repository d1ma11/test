package dto;

import jakarta.persistence.*;

@Entity
@Table(name = "habitat", schema = "animals")
public class Habitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idArea;
    private String area;

    public Habitat() {
    }

    public Habitat(int idArea, String area) {
        this.idArea = idArea;
        this.area = area;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
