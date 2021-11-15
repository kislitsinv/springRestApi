package xyz.vkislitsin.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String name;
    private List<OweModel> owes;
    private List<OweModel> owed_by;
    private BigDecimal balance;
}
