package dto;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "animal_type", schema = "animals")
public class AnimalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idType;
    private String typeName;
    private boolean isWild;

    public AnimalType() {
    }

    public AnimalType(String type, boolean isWild) {
        this.typeName = type;
        this.isWild = isWild;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String type) {
        this.typeName = type;
    }

    public boolean isWild() {
        return isWild;
    }

    public void setWild(boolean wild) {
        isWild = wild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalType that = (AnimalType) o;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }

    @Override
    public String toString() {
        return "AnimalType{" +
                "idType=" + idType +
                ", type='" + typeName + '\'' +
                ", isWild=" + isWild +
                '}';
    }
}
