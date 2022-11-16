package model;

import java.io.Serializable;

import lombok.*;
import mongo.AbstractEntity;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@EqualsAndHashCode
//@BsonDiscriminator(key="_clazz")
public abstract class EquipmentType implements Serializable {

    @BsonProperty("equipmentDescription")
    protected String equipmentDescription;
}
