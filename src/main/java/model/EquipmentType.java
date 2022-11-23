package model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@ToString
@NoArgsConstructor
public enum EquipmentType implements Serializable {
    BASIC("Basic"),
    EXTENDED("Extended"),
    DELUXE("Deluxe");

    @Getter
    @BsonProperty("typeInfo")
    @JsonbProperty("typeInfo")
    private String typeinfo;

    @BsonCreator
    @JsonbCreator
    EquipmentType(@NonNull @BsonProperty("typeInfo") @JsonbProperty("typeInfo") String typeinfo) {
        this.typeinfo = typeinfo;
    }
}
