package model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

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
    ClientType(@NonNull @BsonProperty("typeInfo") String typeInfo, @NonNull @BsonProperty("discount") Double discount) {
        this.typeInfo = typeInfo;
        this.discount = discount;
    }

    public double applyDiscount(double price) {
        return price * discount;
    }
}
