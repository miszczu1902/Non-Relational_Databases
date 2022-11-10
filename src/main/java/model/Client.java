package model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "address", "clientType"})
public class Client implements Serializable {

    @BsonCreator
    public Client(@NonNull @BsonProperty("personalID") String personalID, @NonNull @BsonProperty("firstName") String firstName,
                  @NonNull @BsonProperty("lastName") String lastName, @NonNull @BsonProperty("address") Address address,
                  @BsonProperty("clientType") ClientType clientType) {
        this.personalID = personalID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
    }

    @Getter
    @NonNull
    @BsonProperty("personalID")
    private String personalID;

    @NonNull
    @BsonProperty("firstName")
    private String firstName;

    @NonNull
    @BsonProperty("lastName")
    private String lastName;

    @NonNull
    @BsonProperty("address")
    private Address address;

    @BsonProperty("clientType")
    private ClientType clientType = ClientType.STANDARD;
}
