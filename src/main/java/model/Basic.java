package model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Basic extends EquipmentType {

    protected boolean microwave = true;
    protected boolean kettle = true;

    public Basic(String equipmentDescription) {
        super(equipmentDescription);
    }

    @Override
    public String getActualEquipmentType() {
        return new ToStringBuilder(this)
                .append("description", equipmentDescription)
                .append("microwave", microwave)
                .append("kettle", kettle)
                .toString();
    }
}
