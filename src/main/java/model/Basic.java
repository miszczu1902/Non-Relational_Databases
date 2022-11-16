package model;

import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
//@BsonDiscriminator(key="_clazz", value="basic")
public class Basic extends EquipmentType {
    @BsonProperty("microwave")
    protected boolean microwave;

    @BsonProperty("kettle")
    protected boolean kettle;

    @BsonCreator
    public Basic(@BsonProperty("equipmentDescription") String equipmentDescription,
                 @BsonProperty("microwave") boolean microwave, @BsonProperty("kettle") boolean kettle) {
        this.equipmentDescription = equipmentDescription;
        this.microwave = microwave;
        this.kettle = kettle;
    }
}
