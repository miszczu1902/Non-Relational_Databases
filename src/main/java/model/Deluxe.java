package model;

import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
//@BsonDiscriminator(key="_clazz", value="deluxe")
public class Deluxe extends Extended {

    @BsonProperty("airConditioning")
    protected boolean airConditioning;

    @BsonCreator
    public Deluxe(@BsonProperty("equipmentDescription") String equipmentDescription,
                  @BsonProperty("microwave") boolean microwave, @BsonProperty("kettle") boolean kettle,
                  @BsonProperty("tv") boolean tv, @BsonProperty("balcony") boolean balcony,
                  @BsonProperty("airConditioning") boolean airConditioning) {
        super(equipmentDescription, microwave, kettle, tv, balcony);
        this.airConditioning = airConditioning;
    }
}
