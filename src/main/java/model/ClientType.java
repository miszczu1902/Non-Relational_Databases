package model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public enum ClientType implements Serializable {

    STANDARD("Standard", 1d),
    PREMIUM("Premium", 0.85);

    @NonNull
    @Getter
    private String typeInfo;
    @NonNull
    @Getter
    private Double discount;

    public double applyDiscount(double price) {
        return price * discount;
    }
}
