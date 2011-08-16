package net.bajobongo.ch.faktury.gui;

import javax.faces.bean.ManagedProperty;


public class AccountAware {
    @ManagedProperty("#{account}")
    Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
