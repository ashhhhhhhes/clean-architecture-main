## UseCase 

## 유스케이스의 입력 유효성 검증

#### SendMoneyUseCase

- 넓은 서비스 문제를 피하기 위해 모든 유스케이스를 분리된 각각의 서비스로 만든다.
- 유스케이스는 비즈니스 규칙을 검증할 책임이 있다. 이는 도메인 엔티티와 책임을 공유한다.
- 유스케이스 계층은 유효성 검사를 수행하지 않는다. (유스케이스는 입력 유효성을 책임지지 않는다.)

> 애플리케이션 계층에서 입력유효성을 검증해야 하는 이유는, 그렇게 하지 않을 경우 애플리케이션 코어의 바깥쪽으로부터 유효하지 않은 입력값을 받게되고, 모델의 상태를 해칠 수 있기 때문이다.

### 입력 모델(input model)에서 입력 유효성 검증하기.

### SendMoneyCommand
- `송금하기` 입력 모델인 **SendMoneyCommand의 생성자 내에서 유효성 검사를 수행한다.**
- 입력 유효성 검사는 애플리케이션 계층의 책임에 해당한다.
- 유스 케이스에 입력을 전달하기 전에 입력 유효성을 검증하여 유스케이스 계층에 전달한다.
- 입력 모델에 유효성 검증 코드를 추가하면서 유스케이스 규현체 주위에 사실상 **오류 방지 계층(anti corruption layer)을 만들었다.**

예제 코드 :
```java
@Getter
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    // final 필드로 선언하여 불변 필드로 만든다.
    // Bean Validation을 사용하여 null 체크를 수행한다.
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
```

### SelfValidating
- 입력 모델의 유효성 검사를 위한 추상 클래스

```java
package com.ash.clean.buckpal.account.common;

import jakarta.validation.*;

import java.util.Set;

public abstract class SelfValidating<T> {

  private Validator validator;

  public SelfValidating() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Evaluates all Bean Validations on the attributes of this
   * instance.
   */
  protected void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate((T) this);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
```

