package com.mybank.domain;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description="Bank Accounts")
public class Account {
	
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@ApiModelProperty(notes="Account number lenght should have atleaset 17 Characters")
	private String accountId;
	//TODO obfuscation and masking
	private String accountNum;
	private String balance;
	private String accessTime;
	
	
	
	public Account() {
		super();
	}

	public Account(String accountId, String accountNum, String balance, String accessTime) {
		super();
		this.accountId = accountId;
		this.accountNum = accountNum;
		this.balance = balance;
		this.accessTime = accessTime;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

}
