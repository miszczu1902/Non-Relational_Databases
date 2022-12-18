package model.equipmentType;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.StringJoiner;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(defaultKeyspace = "hotel")
@CqlName("equipment_types")
public class Extended extends Basic {

    @CqlName("tv")
    protected boolean tv = true;

    @CqlName("balcony")
    protected boolean balcony = true;

    public Extended(String equipmentDescription) {
        super(equipmentDescription);
    }

    @Override
    public String getActualEquipmentType() {
        return new StringJoiner(", ", Extended.class.getSimpleName() + "[", "]")
                .add("tv=" + tv)
                .add("balcony=" + balcony)
                .add("microwave=" + microwave)
                .add("kettle=" + kettle)
                .add("id=" + id)
                .add("equipmentDescription='" + equipmentDescription + "'")
                .toString();
    }
}
