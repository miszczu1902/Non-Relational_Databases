package model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Address implements Serializable {

    @BsonCreator
    public Address(@NonNull @BsonProperty("id") long id, @NonNull @BsonProperty("city") String city,
                   @NonNull @BsonProperty("street") String street,
                   @NonNull @BsonProperty("number") String number) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.number = number;
    }
    @NonNull
    @BsonProperty("id")
    private long id;

    @NonNull
    @BsonProperty("city")
    private String city;

    @NonNull
    @BsonProperty("street")
    private String street;

    @NonNull
    @BsonProperty("number")
    private String number;
}
