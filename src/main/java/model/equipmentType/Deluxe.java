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
public class Deluxe extends Extended {

    @CqlName("airConditioning")
    protected boolean airConditioning = true;

    public Deluxe(String equipmentDescription) {
        super(equipmentDescription);
    }

    @Override
    public String getActualEquipmentType() {
        return new StringJoiner(", ", Deluxe.class.getSimpleName() + "[", "]")
                .add("airConditioning=" + airConditioning)
                .add("tv=" + tv)
                .add("balcony=" + balcony)
                .add("microwave=" + microwave)
                .add("kettle=" + kettle)
                .add("id=" + id)
                .add("equipmentDescription='" + equipmentDescription + "'")
                .toString();
    }
}
