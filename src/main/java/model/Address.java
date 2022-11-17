package model;

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
    public Address(@NonNull @BsonProperty("id") long id, @BsonProperty("city") String city,
                   @BsonProperty("street") String street,
                   @BsonProperty("number") String number) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.number = number;
    }
    @NonNull
    @BsonProperty("id")
    private long id;


    @BsonProperty("city")
    private String city;


    @BsonProperty("street")
    private String street;


    @BsonProperty("number")
    private String number;
}
