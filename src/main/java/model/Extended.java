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
@DiscriminatorValue("extended")
@NoArgsConstructor
public class Extended extends Basic {

    @NotNull
    @Column(name = "tv")
    protected boolean tv = true;

    @NotNull
    @Column(name = "balcony")
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
