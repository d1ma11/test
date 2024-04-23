package service;

import dto.db_objects.*;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/mts";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234567890";

    private static ResultSet getTableRecord(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static List<TableRecord> getCreatures() throws SQLException {
        ResultSet resultSet = getTableRecord("SELECT id_creature, name, type_id, age FROM animals.creature");
        List<TableRecord> records = new ArrayList<>();
        while (resultSet.next()) {
            Creature creature = new Creature();
            creature.setIdCreature(resultSet.getLong("id_creature"));
            creature.setName(resultSet.getString("name"));
            creature.setTypeId(resultSet.getInt("type_id"));
            creature.setAge(resultSet.getShort("age"));
            records.add(creature);
        }
        resultSet.close();
        return records;
    }

    public static List<TableRecord> getHabitats() throws SQLException {
        ResultSet resultSet = getTableRecord("SELECT id_area, area FROM animals.habitat");
        List<TableRecord> records = new ArrayList<>();
        while (resultSet.next()) {
            Habitat habitat = new Habitat();
            habitat.setIdArea(resultSet.getInt("id_area"));
            habitat.setArea(resultSet.getString("area"));
            records.add(habitat);
        }
        resultSet.close();
        return records;
    }

    public static List<TableRecord> getProviders() throws SQLException {
        ResultSet resultSet = getTableRecord("SELECT id_provider, name, phone FROM animals.provider");
        List<TableRecord> records = new ArrayList<>();
        while (resultSet.next()) {
            Provider provider = new Provider();
            provider.setIdProvider(resultSet.getInt("id_provider"));
            provider.setName(resultSet.getString("name"));
            provider.setPhone(resultSet.getString("phone").trim());
            records.add(provider);
        }
        resultSet.close();
        return records;
    }

    public static List<TableRecord> getAnimalTypes() throws SQLException {
        ResultSet resultSet = getTableRecord("SELECT id_type, type_name, is_wild FROM animals.animal_type");
        List<TableRecord> records = new ArrayList<>();
        while (resultSet.next()) {
            AnimalType animalType = new AnimalType();
            animalType.setIdType(resultSet.getInt("id_type"));
            animalType.setTypeName(resultSet.getString("type_name").trim());
            animalType.setWild(resultSet.getBoolean("is_wild"));
            records.add(animalType);
        }
        resultSet.close();
        return records;
    }
}
