package mappers;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import dao.AddressDao;

@Mapper
public interface AddressMapper {

    @DaoFactory
    AddressDao addressDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    AddressDao addressDao();
}
