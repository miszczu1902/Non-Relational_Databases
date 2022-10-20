package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"beginTime", "endTime", "client", "reservationCost"})
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "room_room_number", nullable = false)
    private Room room;
    @NonNull
    @Column(name = "begin_time", nullable = false)
    private LocalDateTime beginTime;

    @NonNull
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "client_personal_id", nullable = false)
    private Client client;

    @NotNull
    @Column(name = "reservation_cost", nullable = false)
    private double reservationCost;

    public int getRentDays() {
        return endTime.getDayOfYear() - beginTime.getDayOfYear();
    }

    public void calculateReservationCost() {
        reservationCost = client.getClientType()
                .applyDiscount(getRentDays() * room.getPrice());
    }
}
