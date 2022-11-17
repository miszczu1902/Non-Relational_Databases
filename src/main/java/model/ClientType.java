package model;

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
    private String typeInfo;

    @Getter
    @BsonProperty("discount")
    private Double discount;

    @BsonCreator
    ClientType(@BsonProperty("typeInfo") String typeInfo, @BsonProperty("discount") Double discount) {
        this.typeInfo = typeInfo;
        this.discount = discount;
    }

    public double applyDiscount(double price) {
        return price * discount;
    }
}
