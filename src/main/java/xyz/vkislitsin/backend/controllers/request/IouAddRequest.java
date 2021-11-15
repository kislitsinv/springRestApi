package xyz.vkislitsin.backend.controllers.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IouAddRequest {
    @NotNull
    private String lender;
    @NotNull
    private String borrower;
    @NotNull
    private BigDecimal amount;
}
