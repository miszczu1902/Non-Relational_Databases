package model;

import java.io.Serializable;
import java.util.StringJoiner;
import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity(defaultKeyspace = "hotel")
@CqlName("equipment_types")
public class EquipmentType implements Serializable {

    @CqlName("eq_id")
    @PartitionKey
    protected UUID id;

    @CqlName("eq_description")
    protected String equipmentDescription;

    public EquipmentType(String equipmentDescription) {
        this.equipmentDescription = equipmentDescription;
    }

    public String getActualEquipmentType() {
        return new StringJoiner(", ", EquipmentType.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("equipmentDescription='" + equipmentDescription + "'")
                .toString();
    }
}
