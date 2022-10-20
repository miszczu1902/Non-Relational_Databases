package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

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
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @Getter
    @NonNull
    @Column(name = "personal_id", nullable = false)
    private String personalID;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_address_id")
    private Address address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClientType clientType = ClientType.STANDARD;

//    @NotNull
//    @Column(name = "archive_status", nullable = false)
//    private boolean archive = false;
}
