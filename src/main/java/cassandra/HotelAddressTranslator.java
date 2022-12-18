package cassandra;

import com.datastax.oss.driver.api.core.addresstranslation.AddressTranslator;
import com.datastax.oss.driver.api.core.context.DriverContext;
import lombok.NonNull;

import java.net.InetSocketAddress;

public class HotelAddressTranslator implements AddressTranslator {

    public HotelAddressTranslator(DriverContext dctx) {
    }

    @NonNull
    @Override
    public InetSocketAddress translate(@NonNull InetSocketAddress inetSocketAddress) {
        String hostAddress = inetSocketAddress.getAddress().getHostAddress();
        String hostname = inetSocketAddress.getHostName();

        return switch (hostAddress) {
            case "172.24.0.2" -> new InetSocketAddress("localhost", 9042);
            case "172.24.0.3" -> new InetSocketAddress("localhost", 9043);
            default -> throw new RuntimeException("Wrong address");
        };
    }

    @Override
    public void close() {

    }
}
