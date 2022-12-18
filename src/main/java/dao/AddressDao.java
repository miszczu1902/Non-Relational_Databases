package dao;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.mapper.annotations.*;
import model.Address;

@Dao
public interface AddressDao {

    @Insert
    void createAddress(Address address);

    @GetEntity
    Address readAddress(ResultSet resultSet);

    @Update
    void updateAddress(Address address);

    @Delete
    void deleteAddress(Address address);
}
