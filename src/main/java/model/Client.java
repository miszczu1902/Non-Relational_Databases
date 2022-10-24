package model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "address", "clientType"})
public class Client implements Serializable {

    @Getter
    @NonNull
    private String personalID;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private Address address;

    private ClientType clientType = ClientType.STANDARD;
}
