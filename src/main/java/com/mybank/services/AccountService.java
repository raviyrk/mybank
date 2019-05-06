package com.mybank.services;

import com.mybank.domain.Account;

public interface AccountService {
		public boolean withDraw(String accountId, Long withDrawAmount);
		public boolean deposit(String accountId, Long depositAmount);
		public boolean transfer(String fromAccountId, String toAccountId,Long transferAmt);
		public boolean updateAccount(Account account);
}
