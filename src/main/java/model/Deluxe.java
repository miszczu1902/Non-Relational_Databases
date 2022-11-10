package model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Deluxe extends Extended {

    @BsonProperty("airConditioning")
    protected boolean airConditioning = true;

    @BsonCreator
    public Deluxe(@BsonProperty("equipmentDescription") String equipmentDescription) {
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
