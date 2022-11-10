package model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import mongo.AbstractEntity;
import org.apache.commons.math3.util.Precision;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"beginTime", "endTime", "client", "reservationCost"}, callSuper = true)
public class Reservation extends AbstractEntity implements Serializable {

    @BsonCreator
    public Reservation(@NonNull @BsonProperty("_id") UUID id, @NonNull @BsonProperty("room") Room room,
                       @NonNull @BsonProperty("beginTime") LocalDateTime beginTime,
                       @NonNull @BsonProperty("endTime") LocalDateTime endTime,
                       @NonNull @BsonProperty("client") Client client, @BsonProperty("reservationCost")
                       double reservationCost) {
        super(id);
        this.room = room;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.client = client;
        this.reservationCost = reservationCost;
    }

    @NonNull
    @BsonProperty("room")
    private Room room;
    @NonNull
    @BsonProperty("beginTime")
    private LocalDateTime beginTime;

    @NonNull
    @BsonProperty("endTime")
    private LocalDateTime endTime;

    @NonNull
    @BsonProperty("client")
    private Client client;

    @BsonProperty("reservationCost")
    private double reservationCost;

    public int getRentDays() {
        return endTime.getDayOfYear() - beginTime.getDayOfYear();
    }

    public void calculateReservationCost() {
        reservationCost = Precision.round(client.getClientType()
                .applyDiscount(getRentDays() * room.getPrice()), 2);
    }
}
