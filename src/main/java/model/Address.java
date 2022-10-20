package model;

import jakarta.persistence.*;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
public class Address implements Serializable {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(name = "city", nullable = false)
    private String city;

    @NonNull
    @Column(name = "street", nullable = false)
    private String street;

    @NonNull
    @Column(name = "number", nullable = false)
    private String number;
}
