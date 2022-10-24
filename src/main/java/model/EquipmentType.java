package model;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public abstract class EquipmentType implements Serializable {

    protected Long id;
    protected String equipmentDescription;

    public EquipmentType(String equipmentDescription) {
        this.equipmentDescription = equipmentDescription;
    }

    public abstract String getActualEquipmentType();
}
