package model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Extended extends Basic {

    @BsonProperty("tv")
    protected boolean tv = true;

    @BsonProperty("balcony")
    protected boolean balcony = true;

    @BsonCreator
    public Extended(@BsonProperty("equipmentDescription") String equipmentDescription) {
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
