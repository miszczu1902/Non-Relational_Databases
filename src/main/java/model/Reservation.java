package model;

import java.io.Serializable;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"beginTime", "endTime", "clientId", "reservationCost", "roomNumber"})
@Entity(defaultKeyspace = "hotel")
@CqlName("res_by_client")
public class Reservation implements Serializable {

    @CqlName("id")
    @PartitionKey
    private UUID id;

    @NonNull
    @CqlName("roomNumber")
    private Integer roomNumber;

    @NonNull
    @CqlName("beginTime")
    private LocalDate beginTime;

    @NonNull
    @CqlName("endTime")
    private LocalDate endTime;

    @NonNull
    @CqlName("clientId")
    private String clientId;

    @CqlName("reservationCost")
    private double reservationCost;
}
