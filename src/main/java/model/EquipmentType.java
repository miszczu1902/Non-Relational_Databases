package model;

import java.io.Serializable;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public abstract class EquipmentType implements Serializable {

    @BsonProperty("id")
    protected Long id;

    @BsonProperty("equipmentDescription")
    protected String equipmentDescription;

    @BsonCreator
    public EquipmentType(@BsonProperty("equipmentDescription") String equipmentDescription) {
        this.equipmentDescription = equipmentDescription;
    }

    public abstract String getActualEquipmentType();
}
