package model.clientType;

import java.io.Serializable;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
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
    PREMIUM("Premium", 0.8d);

    @NonNull
    @Getter
    @CqlName("typeInfo")
    private String typeInfo;

    @NonNull
    @Getter
    @CqlName("discount")
    private Double discount;

    public double applyDiscount(double price) {
        return price * discount;
    }
}
