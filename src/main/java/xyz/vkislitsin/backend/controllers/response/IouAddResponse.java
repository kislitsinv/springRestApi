package xyz.vkislitsin.backend.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.vkislitsin.backend.models.UserModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IouAddResponse {
    private UserModel lender;
    private UserModel borrower;
}
