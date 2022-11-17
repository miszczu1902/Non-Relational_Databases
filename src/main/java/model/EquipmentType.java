package model;

import java.io.Serializable;

@ToString
@NoArgsConstructor
public enum EquipmentType implements Serializable {
    BASIC("Basic"),
    EXTENDED("Extended"),
    DELUXE("Deluxe");

    @Getter
    @BsonProperty("typeInfo")
    private String typeinfo;

    EquipmentType(@NonNull @BsonProperty("typeInfo") String typeinfo) {
        this.typeinfo = typeinfo;
    }
}
