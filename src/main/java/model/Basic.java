package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("basic")
@NoArgsConstructor
public class Basic extends EquipmentType {
    @NotNull
    @Column(name = "microwave")
    protected boolean microwave = true;

    @NotNull
    @Column(name = "kettle")
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
