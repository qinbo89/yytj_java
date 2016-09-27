package com.hongbao.dal.model;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;

public class UserBank  extends BaseEntityImpl implements Archivable {

	private static final long serialVersionUID = 1L;

    private String userId;

    private String accountNumber;

    private String accountName;

    private String openingBank;

    private Boolean archive;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank == null ? null : openingBank.trim();
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
}