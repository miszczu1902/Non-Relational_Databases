package model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Address implements Serializable {

    @BsonCreator
    @JsonbCreator
    public Address(@NonNull @BsonProperty("id") @JsonbProperty("id") long id,
                   @BsonProperty("city") @JsonbProperty("city") String city,
                   @BsonProperty("street") @JsonbProperty("street") String street,
                   @BsonProperty("number")  @JsonbProperty("number") String number) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.number = number;
    }
    @NonNull
    @BsonProperty("id")
    @JsonbProperty("id")
    private long id;


    @BsonProperty("city")
    @JsonbProperty("city")
    private String city;


    @BsonProperty("street")
    @JsonbProperty("street")
    private String street;

    @BsonProperty("number")
    @JsonbProperty("number")
    private String number;
}
