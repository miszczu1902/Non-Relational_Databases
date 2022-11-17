package model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"capacity", "price", "equipmentType"})
public class Room implements Serializable {

    @BsonCreator
    public Room(@NonNull@BsonProperty("roomNumber") Integer roomNumber, @BsonProperty("capacity") Integer capacity,
                @BsonProperty("price") Double price, @BsonProperty("equipmentType") EquipmentType equipmentType) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
        this.equipmentType = equipmentType;
    }

    @NonNull
    @BsonProperty("roomNumber")
    private Integer roomNumber;


    @BsonProperty("capacity")
    private Integer capacity;


    @BsonProperty("price")
    private Double price;


    @BsonProperty("equipmentType")
    private EquipmentType equipmentType;
}
