package model;

import lombok.*;
import mongo.AbstractEntity;
import mongo.UniqueIdMgd;
import org.apache.commons.math3.util.Precision;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"room", "beginTime", "endTime", "client", "reservationCost"}, callSuper = true)
public class Reservation extends AbstractEntity implements Serializable {

    @BsonCreator
    public Reservation(@BsonProperty("_id") UniqueIdMgd id,
                       @BsonProperty("room") Room room,
                       @BsonProperty("beginTime") String beginTime,
                       @BsonProperty("endTime") String endTime,
                       @BsonProperty("client") Client client,
                       @BsonProperty("reservationCost") double reservationCost) {
        super(id);
        this.room = room;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.client = client;
        this.reservationCost = reservationCost;
    }

    public Reservation(@NonNull UniqueIdMgd id) {
        super(id);
    }

    @BsonProperty("room")
    private Room room;

    @BsonProperty("beginTime")
    private String beginTime;


    @BsonProperty("endTime")
    private String endTime;


    @BsonProperty("client")
    private Client client;

    @BsonProperty("reservationCost")
    private double reservationCost;

    public int getRentDays() {
        return LocalDateTime.parse(endTime).getDayOfYear() - LocalDateTime.parse(beginTime).getDayOfYear();
    }

    public void calculateReservationCost() {
        reservationCost = Precision.round(client.getClientType()
                .applyDiscount(getRentDays() * room.getPrice()), 2);
    }
}
