package model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Extended extends Basic {

    protected boolean tv = true;

    protected boolean balcony = true;

    public Extended(String equipmentDescription) {
        super(equipmentDescription);
    }

    @Override
    public String getActualEquipmentType() {
        return super.getActualEquipmentType()
                .concat(new ToStringBuilder(this)
                        .append("tv", tv)
                        .append("balcony", balcony)
                        .toString());
    }
}
