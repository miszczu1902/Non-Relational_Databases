package model;

import java.io.Serializable;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.*;
import org.apache.commons.math3.util.Precision;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"beginTime", "endTime", "client", "reservationCost"})
@Entity
@CqlName("reservation")
public class Reservation implements Serializable {

    @CqlName("id")
    private UUID id;

    @NonNull
    @CqlName("room")
    private Room room;

    @NonNull
    @CqlName("beginTime")
    private LocalDateTime beginTime;

    @NonNull
    @CqlName("endTime")
    private LocalDateTime endTime;

    @NonNull
    @CqlName("client")
    private Client client;

    @CqlName("reservationCost")
    private double reservationCost;

    public Reservation(UUID id) {
        this.id = id;
    }

    public int getRentDays() {
        return endTime.getDayOfYear() - beginTime.getDayOfYear();
    }

    public void calculateReservationCost() {
        reservationCost = Precision.round(client.getClientType()
                .applyDiscount(getRentDays() * room.getPrice()), 2);
    }
}
