package model;

import java.io.Serializable;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "address", "clientType"})
@CqlName("clients")
@Entity
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
    @CqlName("address")
    private Address address;

    @CqlName("clientType")
    private ClientType clientType = ClientType.STANDARD;

    public Client(@NonNull String personalID) {
        this.personalID = personalID;
    }
}
