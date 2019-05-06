package com.mybank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybank.domain.Account;
import com.mybank.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepo;
	
	@Override
	public boolean withDraw(String id, Long withDrawAmount) {
		// TODO Auto-generated method stub
		Optional<Account> accountOp = accountRepo.findById(id);

		if (accountOp.isPresent())
		{
					Account acct = accountOp.get();
					// TODO Bank Rules to check for Min account balance;
					Long currentBal =  Long.parseLong(acct.getBalance());
					if (currentBal > withDrawAmount) {
						acct.setBalance(Long.toString(currentBal - withDrawAmount));
						accountRepo.save(acct);
						return true;
					}
		}

		
		return false;
	}

	@Override
	public boolean deposit(String id, Long depositAmount) {
		Optional<Account> accountOp = accountRepo.findById(id);

		if (accountOp.isPresent()) {
			Account acct = accountOp.get();
					acct.setBalance(Long.toString(Long.parseLong(acct.getBalance()) + depositAmount));
					accountRepo.save(acct);
					return true;
		}
		return false;
	}

	@Override
	public boolean transfer(String fromAccountId, String toAccountId, Long trnsFrAmt) {
		Optional<Account> accountOpFrm = accountRepo.findById(fromAccountId);

		Optional<Account> accountOpto = accountRepo.findById(toAccountId);

		if (accountOpFrm.isPresent() && accountOpFrm.isPresent())
		{
			
			Account acctFrm = accountOpFrm.get();
			Account acctTo = accountOpto.get();
			Long toAcctbalance = Long.parseLong(acctTo.getBalance());
			Long frmAcctBalance = Long.parseLong(acctFrm.getBalance());
			if (frmAcctBalance > trnsFrAmt) {
				acctFrm.setBalance(Long.toString(frmAcctBalance - trnsFrAmt));
				accountRepo.save(acctFrm);
				acctTo.setBalance(Long.toString(toAcctbalance + trnsFrAmt));
				accountRepo.save(acctTo);
				return true;
			}
			
		}
		return false;
	}

	@Override
	public boolean updateAccount(Account acct) {
		Optional<Account> accountOp = accountRepo.findById(acct.getAccountId());

		if (!accountOp.isPresent()) {
			return false;
		}
		else 
		{
			Account uptdAcct = accountOp.get();
			uptdAcct.setAccountNum(acct.getBalance());
			uptdAcct.setAccountNum(acct.getAccountNum());
			accountRepo.save(uptdAcct);
			
		}
		return true;
	}

}
