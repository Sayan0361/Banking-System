package service.impl;

import domain.Account;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        String accountNumber = String.format("AC%06d", size);
        return accountNumber;
    }

    @Override
    public String openAccount(String name, String email, String accountType) {
        String costumerId = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();

        Account account = new Account(accountNumber,costumerId,(double)0,accountType);
        // save
        accountRepository.save(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found " + accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(account.getAccountNumber(), Type.DEPOSIT, UUID.randomUUID().toString(), amount, LocalDateTime.now());
        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found " + accountNumber));

        if(account.getBalance().compareTo(amount) < 0) throw new RuntimeException("Insufficient Balance");

        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(account.getAccountNumber(), Type.WITHDRAW, UUID.randomUUID().toString(), amount, LocalDateTime.now());
        transactionRepository.add(transaction);
    }


}
