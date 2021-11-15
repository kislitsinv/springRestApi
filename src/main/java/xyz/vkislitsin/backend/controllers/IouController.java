package xyz.vkislitsin.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.vkislitsin.backend.controllers.request.IouAddRequest;
import xyz.vkislitsin.backend.controllers.response.IouAddResponse;
import xyz.vkislitsin.backend.entities.Loan;
import xyz.vkislitsin.backend.services.IIouService;

@RestController
@RequiredArgsConstructor
@Validated
public class IouController {

    private final IIouService iIouService;

    @PostMapping(value = "/iou", consumes = "application/json")
    public IouAddResponse addNewUser(@RequestBody IouAddRequest iouRequest) throws Exception {
        if (iouRequest == null) {
            throw new Exception("No payload provided: you must transfer lender, borrower and amount");
        }
        Loan newLoan = iIouService.addNewLoan(
                iouRequest.getLender(),
                iouRequest.getBorrower(),
                iouRequest.getAmount());
        IouAddResponse response = iIouService.getResponseFromLoanEntity(newLoan);
        return response;
    }
}
