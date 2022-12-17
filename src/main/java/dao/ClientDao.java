package dao;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.mapper.annotations.*;
import model.Client;
//import queryProviders.ClientQueryProvider;

@Dao
public interface ClientDao {

    @Insert
    void createClient(Client client);

//    @QueryProvider(providerClass = ClientQueryProvider.class, entityHelpers = {Client.class})
    @GetEntity
    Client readClient(ResultSet resultSet);

    @Update
    void updateClient(Client client);

    @Delete
    void deleteClient(Client client);
}
