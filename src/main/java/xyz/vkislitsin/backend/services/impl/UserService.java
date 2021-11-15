package xyz.vkislitsin.backend.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import xyz.vkislitsin.backend.entities.Loan;
import xyz.vkislitsin.backend.entities.User;
import xyz.vkislitsin.backend.models.OweModel;
import xyz.vkislitsin.backend.models.UserModel;
import xyz.vkislitsin.backend.repositories.LoanRepository;
import xyz.vkislitsin.backend.repositories.UserRepository;
import xyz.vkislitsin.backend.services.IUserService;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Override
    public List<UserModel> getAllUsers(boolean sort) throws Exception {
        List<User> users;
        if (!sort) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByOrderByNameAsc();
        }
        if (!users.isEmpty()) {
            return users
                    .stream()
                    .map(u -> userRepositoryToUserModelDto(u))
                    .collect(Collectors.toList());
        } else {
            throw new Exception("No User entity was found!");
        }
    }

    @Override
    public UserModel getUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new Exception("No user after loan operation was found!");
        }
        return userRepositoryToUserModelDto(user.get());
    }

    @Override
    public UserModel addNewUser(String userName) throws Exception {

        if (isUserExist(userName)) {
            throw new Exception("Username already exists!");
        }

        User newUser = new User();
        newUser.setId(setIdForNewUser());
        newUser.setName(userName);
        newUser.setBalance(new BigDecimal(0));
        newUser = userRepository.save(newUser);
        if (newUser == null) {
            throw new Exception("Couldn't save new user!");
        }
        List<OweModel> emptyOwes = new ArrayList<>();
        UserModel returnUser = new UserModel();
        returnUser.setName(userName);
        returnUser.setBalance(newUser.getBalance());
        returnUser.setOwes(emptyOwes);
        returnUser.setOwed_by(emptyOwes);
        return returnUser;
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    // here we map User entity to UserModel DTO
    private UserModel userRepositoryToUserModelDto(User userEntity) {
        UserModel user = new UserModel();
        user.setName(userEntity.getName());
        user.setOwes(findUserOfLoans(userEntity.getId()));
        user.setOwed_by(findLoansOfUser(userEntity.getId()));
        user.setBalance(setBalanceOnExistingInfo(user));
        return user;
    }

    // here we map Loan entity to OweModel DTO
    private OweModel loanRepositoryToOweModelDto(Loan loan, String userName) {
        OweModel loanDto = new OweModel();
        loanDto.setCount(loan.getAmount());
        loanDto.setName(userName);
        return loanDto;
    }

    // method to find what loans did user give
    private List<OweModel> findUserOfLoans(Long userId) {
        List<Loan> loans = loanRepository.findAllByBorrowerId(userId);
        return loans
                .stream()
                .map(l -> loanRepositoryToOweModelDto(l, getUserNameById(l.getDebtor().getId())))
                .collect(Collectors.toList());
    }

    // method to find what loans has been given to user
    private List<OweModel> findLoansOfUser(Long userId) {
        List<Loan> loans = loanRepository.findAllByDebtorId(userId);
        return loans
                .stream()
                .map(l -> loanRepositoryToOweModelDto(l, getUserNameById(l.getBorrower().getId())))
                .collect(Collectors.toList());
    }

    // method to find userName by its id
    private String getUserNameById(Long id) {
        return userRepository.findNameById(id);
    }

    // method to count balance
    private BigDecimal setBalanceOnExistingInfo(UserModel user) {
        BigDecimal planIncome = new BigDecimal(0);
        BigDecimal planOutcome = new BigDecimal(0);

        for (OweModel income : user.getOwes()) {
            planIncome = planIncome.add(income.getCount());
        }
        for (OweModel outcome : user.getOwed_by()) {
            planOutcome = planOutcome.add(outcome.getCount());
        }
        return planIncome.subtract(planOutcome);
    }

    // for User id set
    private Long setIdForNewUser() {
        return userRepository.findMaxId() + 1;
    }

    // check if user with this username exists
    private boolean isUserExist(String userName) {
        return userRepository.findByName(userName) != null;
    }
}
