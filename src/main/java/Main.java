import cassandra.CassandraClient;
import com.datastax.oss.driver.api.core.CqlSession;
import managers.HotelManager;

public class Main {
    public static void main(String[] args) {
        CqlSession session = new CassandraClient().getSession();
        HotelManager hotelManager = new HotelManager(session);

    }
}
