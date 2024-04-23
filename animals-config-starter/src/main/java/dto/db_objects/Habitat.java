package dto.db_objects;

public class Habitat implements TableRecord {

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

    @Override
    public String toString() {
        return "Habitat{" +
                "idArea=" + idArea +
                ", area='" + area + '\'' +
                '}';
    }
}
