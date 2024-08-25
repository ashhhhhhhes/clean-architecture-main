package com.ash.clean.buckpal.account.application.port.in;

import com.ash.clean.buckpal.account.common.SelfValidating;
import com.ash.clean.buckpal.account.domain.Account;
import com.ash.clean.buckpal.account.domain.Account.AccountId;
import com.ash.clean.buckpal.account.domain.Money;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public
class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final AccountId sourceAccountId;

    @NotNull
    private final AccountId targetAccountId;

    @NotNull
    private final Money money;

    public SendMoneyCommand(
            AccountId sourceAccountId,
            AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}