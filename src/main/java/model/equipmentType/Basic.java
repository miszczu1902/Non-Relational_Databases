package model.equipmentType;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.EquipmentType;

import java.util.StringJoiner;

@EqualsAndHashCode(callSuper = true, exclude = {"microwave", "kettle"})
@NoArgsConstructor
@Entity(defaultKeyspace = "hotel")
@CqlName("equipment_types")
public class Basic extends EquipmentType {

    @CqlName("microwave")
    protected boolean microwave = true;

    @CqlName("kettle")
    protected boolean kettle = true;

    public Basic(String equipmentDescription) {
        super(equipmentDescription);
    }

    @Override
    public String getActualEquipmentType() {
        return new StringJoiner(", ", Basic.class.getSimpleName() + "[", "]")
                .add("microwave=" + microwave)
                .add("kettle=" + kettle)
                .add("id=" + eqId)
                .add("equipmentDescription='" + equipmentDescription + "'")
                .toString();
    }
}
