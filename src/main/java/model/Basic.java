package model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Basic extends EquipmentType {
    @BsonProperty("microwave")
    protected boolean microwave = true;

    @BsonProperty("kettle")
    protected boolean kettle = true;

    @BsonCreator
    public Basic(@BsonProperty("equipmentDescription") String equipmentDescription) {
        super(equipmentDescription);
    }

    @Override
    public String getActualEquipmentType() {
        return new ToStringBuilder(this)
                .append("description", equipmentDescription)
                .append("microwave", microwave)
                .append("kettle", kettle)
                .toString();
    }
}
