package model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "address", "clientType"})
public class Client implements Serializable {

    @BsonCreator
    public Client(@BsonProperty("personalID") String personalID, @BsonProperty("firstName") String firstName,
                  @BsonProperty("lastName") String lastName, @BsonProperty("address") Address address,
                  @BsonProperty("clientType") ClientType clientType) {
        this.personalID = personalID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
    }

    @Getter
    @BsonId
    @NonNull
    @BsonProperty("personalID")
    private String personalID;


    @BsonProperty("firstName")
    private String firstName;


    @BsonProperty("lastName")
    private String lastName;


    @BsonProperty("address")
    private Address address;

    @BsonProperty("clientType")
    private ClientType clientType = ClientType.STANDARD;
}
