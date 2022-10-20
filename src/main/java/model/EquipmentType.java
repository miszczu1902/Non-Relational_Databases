package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "equipment_type")
@Access(AccessType.FIELD)
public abstract class EquipmentType implements Serializable {

    @Id
    @NotNull
    @Column(name = "eq_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(name = "equipment_description")
    protected String equipmentDescription;

    public EquipmentType(String equipmentDescription) {
        this.equipmentDescription = equipmentDescription;
    }

    public abstract String getActualEquipmentType();
}
