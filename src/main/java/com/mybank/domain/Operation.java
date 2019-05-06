package com.mybank.domain;

public class Operation {

	private String bankOp;
	private String amount;
	private String fromAccount;
	private String toAccount;
	
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getBankOp() {
		return bankOp;
	}
	public void setBankOp(String bankOp) {
		this.bankOp = bankOp;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
}
