package model;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.*;
import model.clientType.ClientType;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "clientType", "addressId", "discount"})
@Entity(defaultKeyspace = "hotel")
@CqlName("clients_id")
public class Client implements Serializable {

    @Getter
    @NonNull
    @PartitionKey
    @CqlName("personalID")
    private String personalID;

    @NonNull
    @CqlName("firstName")
    private String firstName;

    @NonNull
    @CqlName("lastName")
    private String lastName;

    @NonNull
    @CqlName("addressId")
    private UUID addressId;

    @CqlName("clientType")
    private String clientType = ClientType.STANDARD.getTypeInfo();

    @CqlName("discount")
    private Double discount = ClientType.STANDARD.getDiscount();
}
