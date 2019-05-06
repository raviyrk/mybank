package com.mybank.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mybank.domain.Account;
import com.mybank.domain.Operation;
import com.mybank.exception.AccountNotFoundException;
import com.mybank.repositories.AccountRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepo;
	
	//TODO Refactor code to use Service Layer

	@GetMapping("/account")
	@ApiOperation(value = "get all accounts", notes = "gets all account by accounts ")
	public List<Account> getAccounts() {
		return accountRepo.findAll();

	}

	@GetMapping("/account/{id}")
	@ApiOperation(value = "Account by userid", notes = "gets the account by account userid")
	public Resource<Account> getAccount(@PathVariable String id) {

		Optional<Account> accoutOp = accountRepo.findById(id);
		if (!accoutOp.isPresent()) {

			throw new AccountNotFoundException("No account Exists with this id " + id);
		}

		Resource<Account> resource = new Resource<Account>(accoutOp.get());

		return resource;
	}

	@PostMapping("/account")
	@ApiOperation(value = " Create Account", notes = "Creates the account")
	public ResponseEntity<Object> createAccount(@RequestBody Account account) {
		Account newAccount = accountRepo.save(account);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newAccount.getAccountId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/account/{id}")
	@ApiOperation(value = " Delete Account", notes = "Deletes the account")
	
	public void deleteStudent(@PathVariable String id) {
		accountRepo.deleteById(id);
	}

	
	@PutMapping("/account")
	@ApiOperation(value = " Update Account", notes = "Update the account")
	public ResponseEntity<Object> updateAccount(@RequestBody Account acct) {
		Optional<Account> accountOp = accountRepo.findById(acct.getAccountId());

		if (!accountOp.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		else {
			Account uptdAcct = accountOp.get();
			uptdAcct.setBalance(acct.getAccountNum());
			accountRepo.save(uptdAcct);
			
		}

		return ResponseEntity.noContent().build();
	}

	
	@PostMapping("/account/{id}")
	@ApiOperation(value = " WithDraw / Deposit Account", notes = "Depoist / withdraw the account")
	
	public ResponseEntity<Object> accountOperation(@RequestBody Operation operation, @PathVariable String id) {

		Optional<Account> accountOp = accountRepo.findById(id);

		if (!accountOp.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		else
		{
			Account acct = accountOp.get();
			switch (operation.getBankOp())
			{
				case "withDraw": {
					// TODO Bank Rules to check for Min account balance;
					Long withdrawVal = Long.parseLong(operation.getAmount());
					Long currentBal =  Long.parseLong(acct.getBalance());
					if (currentBal > withdrawVal) {
						acct.setBalance(Long.toString(currentBal - withdrawVal));
					}
					break;
				}
				case "deposit": {
					Long depositAmt = Long.parseLong(operation.getAmount());
					acct.setBalance(Long.toString(Long.parseLong(acct.getBalance()) + depositAmt));
					break;
				}

			}
			accountRepo.save(acct);
		}

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/transfer")
	public ResponseEntity<Object> bankOperation(@RequestBody Operation operation) {

		Optional<Account> accountOpFrm = accountRepo.findById(operation.getFromAccount());

		Optional<Account> accountOpto = accountRepo.findById(operation.getToAccount());

		if (!accountOpFrm.isPresent() || !accountOpFrm.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Account acctFrm = accountOpFrm.get();
			Account acctTo = accountOpto.get();
			Long toAcctbalance = Long.parseLong(acctTo.getBalance());
			Long frmAcctBalance = Long.parseLong(acctFrm.getBalance());
			Long trnsFrAmt = Long.parseLong(operation.getAmount());
			if (frmAcctBalance > trnsFrAmt) {
				acctFrm.setBalance(Long.toString(frmAcctBalance - trnsFrAmt));
				accountRepo.save(acctFrm);
				acctTo.setBalance(Long.toString(toAcctbalance + trnsFrAmt));
				accountRepo.save(acctTo);
			}

		}

		return ResponseEntity.noContent().build();
	}
}
