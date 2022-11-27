package model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
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
    @JsonbCreator
    public Room(@NonNull @BsonProperty("roomNumber") @JsonbProperty("roomNumber") Integer roomNumber,
                @BsonProperty("capacity") @JsonbProperty("capacity") Integer capacity,
                @BsonProperty("price") @JsonbProperty("price") Double price,
                @BsonProperty("equipmentType") @JsonbProperty("equipmentType") EquipmentType equipmentType) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
        this.equipmentType = equipmentType;
    }

    @NonNull
    @BsonProperty("roomNumber")
    @JsonbProperty("roomNumber")
    private Integer roomNumber;

    @BsonProperty("capacity")
    @JsonbProperty("capacity")
    private Integer capacity;

    @BsonProperty("price")
    @JsonbProperty("price")
    private Double price;

    @BsonProperty("equipmentType")
    @JsonbProperty("equipmentType")
    private EquipmentType equipmentType;
}
