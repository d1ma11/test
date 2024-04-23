package dto.db_objects;

public class Creature implements TableRecord{

    private long idCreature;
    private String name;
    private int typeId;
    private short age;

    public Creature() {
    }

    public Creature(long idCreature, String name, int typeId, short age) {
        this.idCreature = idCreature;
        this.name = name;
        this.typeId = typeId;
        this.age = age;
    }

    public long getIdCreature() {
        return idCreature;
    }

    public void setIdCreature(long idCreature) {
        this.idCreature = idCreature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "idCreature=" + idCreature +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", age=" + age +
                '}';
    }
}
