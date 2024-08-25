package com.ash.clean.buckpal.account.application.port.out;

import com.ash.clean.buckpal.account.domain.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);

}
