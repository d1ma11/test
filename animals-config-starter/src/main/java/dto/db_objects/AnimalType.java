package dto.db_objects;

public class AnimalType implements TableRecord {

    private int idType;
    private String typeName;
    private boolean isWild;

    public AnimalType() {
    }

    public AnimalType(int idType, String typeName, boolean isWild) {
        this.idType = idType;
        this.typeName = typeName;
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

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isWild() {
        return isWild;
    }

    public void setWild(boolean wild) {
        isWild = wild;
    }

    @Override
    public String toString() {
        return "AnimalType{" +
                "idType=" + idType +
                ", typeName='" + typeName + '\'' +
                ", isWild=" + isWild +
                '}';
    }
}
