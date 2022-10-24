package model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Deluxe extends Extended {

    protected boolean airConditioning = true;

    public Deluxe(String equipmentDescription) {
        super(equipmentDescription);
    }

    @Override
    public String getActualEquipmentType() {
        return super.getActualEquipmentType()
                .concat(new ToStringBuilder(this)
                        .append("airConditioning", airConditioning)
                        .toString());
    }
}
