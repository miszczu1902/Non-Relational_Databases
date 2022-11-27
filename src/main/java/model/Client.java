package model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
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
    @JsonbCreator
    public Client(@BsonProperty("personalID") @JsonbProperty("personalID") String personalID,
                  @BsonProperty("firstName") @JsonbProperty("firstName") String firstName,
                  @BsonProperty("lastName") @JsonbProperty("lastName") String lastName,
                  @BsonProperty("address") @JsonbProperty("address") Address address,
                  @BsonProperty("clientType") @JsonbProperty("clientType") ClientType clientType) {
        this.personalID = personalID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
    }

    @Getter
    @NonNull
    @BsonProperty("personalID")
    @JsonbProperty("personalID")
    private String personalID;

    @BsonProperty("firstName")
    @JsonbProperty("firstName")
    private String firstName;

    @BsonProperty("lastName")
    @JsonbProperty("lastName")
    private String lastName;

    @BsonProperty("address")
    @JsonbProperty("address")
    private Address address;

    @BsonProperty("clientType")
    @JsonbProperty("clientType")
    private ClientType clientType = ClientType.STANDARD;
}
