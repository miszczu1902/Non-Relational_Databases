package model;

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
public class Address implements Serializable {

    private Long id;

    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    private String number;
}
