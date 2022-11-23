package model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@ToString
@NoArgsConstructor
public enum ClientType implements Serializable {

    STANDARD("Standard", 1d),
    PREMIUM("Premium", 0.8d);

    @Getter
    @BsonProperty("typeInfo")
    @JsonbProperty("typeInfo")
    private String typeInfo;

    @Getter
    @BsonProperty("discount")
    @JsonbProperty("discount")
    private Double discount;

    @BsonCreator
    @JsonbCreator
    ClientType(@BsonProperty("typeInfo") @JsonbProperty("typeInfo") String typeInfo,
               @BsonProperty("discount") @JsonbProperty("discount") Double discount) {
        this.typeInfo = typeInfo;
        this.discount = discount;
    }

    public double applyDiscount(double price) {
        return price * discount;
    }
}
