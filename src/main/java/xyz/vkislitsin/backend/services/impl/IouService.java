package xyz.vkislitsin.backend.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.vkislitsin.backend.controllers.exceptions.BadArgumentsException;
import xyz.vkislitsin.backend.controllers.response.IouAddResponse;
import xyz.vkislitsin.backend.entities.Loan;
import xyz.vkislitsin.backend.entities.User;
import xyz.vkislitsin.backend.models.UserModel;
import xyz.vkislitsin.backend.repositories.LoanRepository;
import xyz.vkislitsin.backend.services.IIouService;
import xyz.vkislitsin.backend.services.IUserService;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class IouService implements IIouService {

    private final LoanRepository loanRepository;
    private final IUserService iUserService;

    @Override
    public Loan addNewLoan(String lender, String borrower, BigDecimal amount) throws Exception {
        User lenderSubject = iUserService.getUserByName(lender);
        User borrowerSubject = iUserService.getUserByName(borrower);
        if (lenderSubject == null || borrowerSubject == null) {
            throw new BadArgumentsException("No such lender or borrower exists in DB!");
        }
        if (amount.equals(0) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadArgumentsException("Wrong amount provided!");
        }

        Loan loan = new Loan();
        loan.setId(setIdForNewLoan());
        loan.setAmount(amount);
        loan.setBorrower(lenderSubject);
        loan.setDebtor(borrowerSubject);
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public IouAddResponse getResponseFromLoanEntity(Loan loan) throws Exception {
        UserModel lender = iUserService.getUserById(loan.getBorrower().getId());
        UserModel borrower = iUserService.getUserById(loan.getDebtor().getId());
        IouAddResponse response = new IouAddResponse();
        response.setLender(lender);
        response.setBorrower(borrower);
        return response;
    }

    private Long setIdForNewLoan() {
        return loanRepository.findMaxId() + 1;
    }
}
