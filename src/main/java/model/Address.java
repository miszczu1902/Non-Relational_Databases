package model;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity(defaultKeyspace = "hotel")
@CqlName("addresses_id")
public class Address implements Serializable {

    @NonNull
    @PartitionKey
    @CqlName("addressId")
    private UUID addressId;

    @NonNull
    @CqlName("city")
    private String city;

    @NonNull
    @CqlName("street")
    private String street;

    @NonNull
    @CqlName("number")
    private String number;
}
