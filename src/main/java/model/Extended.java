package model;

import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
//@BsonDiscriminator(key="_clazz", value="extended")
public class Extended extends Basic {

    @BsonProperty("tv")
    protected boolean tv;

    @BsonProperty("balcony")
    protected boolean balcony;

    @BsonCreator
    public Extended(@BsonProperty("equipmentDescription") String equipmentDescription,
                    @BsonProperty("microwave") boolean microwave, @BsonProperty("kettle") boolean kettle,
                    @BsonProperty("tv") boolean tv, @BsonProperty("balcony") boolean balcony) {
        super(equipmentDescription, microwave, kettle);
        this.tv = tv;
        this.balcony = balcony;
    }
}
