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
@DiscriminatorValue("deluxe")
@NoArgsConstructor
public class Deluxe extends Extended {

    @NotNull
    @Column(name = "air_conditioning")
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
