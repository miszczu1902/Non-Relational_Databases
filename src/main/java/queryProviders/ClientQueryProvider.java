//package queryProviders;
//
//import cassandra.CassandraNamespaces;
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.core.cql.ResultSet;
//import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
//import com.datastax.oss.driver.api.querybuilder.relation.Relation;
//import com.datastax.oss.driver.api.querybuilder.select.Select;
//import model.Client;
//
//
//import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
//
//public class ClientQueryProvider {
//
//    public ClientQueryProvider(CqlSession session) {
//        this.session = session;
//    }
//
//    public Client read(String personalID) {
//        Select getClientByPersonalID = QueryBuilder
//                .selectFrom(CassandraNamespaces.CLIENT_ID)
//                .all()
//                .where(Relation.column("personalID").isEqualTo(literal(personalID)));
//        ResultSet resultSet = session.execute(getClientByPersonalID.build());
//
//        return resultSet.all().stream()
//                .map(row -> (Client) row.getObject(personalID))
//                .toList().get(0);
//    }
//}
