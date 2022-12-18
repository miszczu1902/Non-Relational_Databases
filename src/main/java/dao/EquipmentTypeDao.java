package dao;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.mapper.annotations.*;
import model.EquipmentType;

@Dao
public interface EquipmentTypeDao {

    @Insert
    void createEquipmentType(EquipmentType equipmentType);

    @GetEntity
    EquipmentType readEquipmentType(ResultSet resultSet);

    @Update
    void updateEquipmentType(EquipmentType equipmentType);

    @Delete
    void deleteEquipmentType(EquipmentType equipmentType);;
}
