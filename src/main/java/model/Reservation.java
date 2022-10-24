package model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.math3.util.Precision;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"beginTime", "endTime", "client", "reservationCost"})
public class Reservation implements Serializable {

    private UUID id;
    @NonNull
    private Room room;
    @NonNull
    private LocalDateTime beginTime;

    @NonNull
    private LocalDateTime endTime;

    @NonNull
    private Client client;

    private double reservationCost;

    public int getRentDays() {
        return endTime.getDayOfYear() - beginTime.getDayOfYear();
    }

    public void calculateReservationCost() {
        reservationCost = Precision.round(client.getClientType()
                .applyDiscount(getRentDays() * room.getPrice()), 2);
    }
}
