package xyz.vkislitsin.backend.services;

import xyz.vkislitsin.backend.controllers.response.IouAddResponse;
import xyz.vkislitsin.backend.entities.Loan;

import java.math.BigDecimal;

public interface IIouService {
    Loan addNewLoan(String lender, String borrower, BigDecimal amount) throws Exception;
    IouAddResponse getResponseFromLoanEntity(Loan loan) throws Exception;
}
