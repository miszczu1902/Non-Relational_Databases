package model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"capacity", "price", "equipmentType"})
public class Room implements Serializable {

    @BsonCreator
    public Room(@NonNull @BsonProperty("roomNumber") Integer roomNumber, @NonNull @BsonProperty("capacity") Integer capacity,
                @NonNull @BsonProperty("price") Double price, @NonNull @BsonProperty("equipmentType") EquipmentType equipmentType) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
        this.equipmentType = equipmentType;
    }

    @NonNull
    @BsonProperty("roomNumber")
    private Integer roomNumber;

    @NonNull
    @BsonProperty("capacity")
    private Integer capacity;

    @NonNull
    @BsonProperty("price")
    private Double price;

    @NonNull
    @BsonProperty("equipmentType")
    private EquipmentType equipmentType;

//    private Long version;
}
