package mappers;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import dao.EquipmentTypeDao;

@Mapper
public interface EquipmentTypeMapper {

    @DaoFactory
    EquipmentTypeDao equipmentTypeDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    EquipmentTypeDao equipmentTypeDao();
}
