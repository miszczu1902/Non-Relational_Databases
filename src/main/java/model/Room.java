package model;

import java.io.Serializable;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"capacity", "price", "equipmentType"})
@CqlName("rooms_id")
@Entity
public class Room implements Serializable {

    @NonNull
    @PartitionKey
    @CqlName("roomNumber")
    private Integer roomNumber;

    @NonNull
    @CqlName("capacity")
    private Integer capacity;

    @NonNull
    @CqlName("price")
    private Double price;

    @NonNull
    @CqlName("equipmentType")
    @ClusteringColumn
    private EquipmentType equipmentType;

    public Room(@NonNull Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
}
