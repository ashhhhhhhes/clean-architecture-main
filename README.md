# 아키텍처적으로 표현력 있는 패키지 구조

--- 

buckpal <br/>
⎣ account <br/>
&nbsp;&nbsp;&nbsp;├- account  <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;├- adapter <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;ㄴ in <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ㄴ web <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ㄴ AccountController <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;├- adapter <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;ㄴ out <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ㄴ persistence <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ㄴ AccountPersistenceAdapter <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ㄴ SpringDataAccountRepository <br/>
&nbsp;&nbsp;&nbsp;├- domain  <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;├- Account <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;ㄴ Activity <br/>
&nbsp;&nbsp;&nbsp;├- application  <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;ㄴ SendMoneyService <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;ㄴ port <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├ in <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;⏐  ㄴ SendMoneyUseCase <br/> 
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ㄴ out <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ㄴ LoadAccountPort <br/>
&nbsp;&nbsp;&nbsp;⏐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ㄴ UpdateAccountStatePort <br/>


#### account 패키지
- Account 와 관련된 유스 케이스를 구현한 모듈 패키지

####  account > domain 패키지
- 도메인 모델이 속한 패키지.

####  account > application 패키지
- 도메인 모델을 둘러싼 서비스 계층을 포함한 패키지.
- SendMoneyService : 인커밍 포트 인터페이스인 SendMoneyUseCase를 구현하고, 아웃고잉 포트 인터페이스이자 영속성 어댑터에 의해구현된 LoadAccountPort와 UpdateAccountStatePort를 사용

#### account > adapter
- 애플리케이션 계층 인커밍 포트를 호출하는 인커밍 어댑터 제공
- 애플리케이션 계층의 아웃고잉 포트에 대한 구현을 제공하는 아웃고잉 어댑터 제공

---
### 육각형 아키텍쳐를 표현한 패키지 구조의 장점
- 협업 시 정확한 이해를 도울 수 있다.
- "아키텍처-코드 갭", "모델코드 갭"을 효과적으로 다룰 수 있다.

### 패키지간의 접근
- adapter 패키지 : 
  - application 패키지 내에 있는 포트 인터페이스를 통하지 않고는 바깥에서 호출되지 않기 때문에 package-private 접근 수준으로 둬도 된다.
- application,domain 패키지: 
  - 일부 클래스들은 public 으로 지정해야한다. 
    - 의도적으로 어댑터에 접근 가능해야 하는 포트들은 public 이어야 한다.
    - 도메인 클래스들은 서비스, 그리고 잠재적으로는 어댑터에도 접근 가능하도록 public 이어야 한다.
  - 서비스는 인커밍 포트 인터페이스 뒤에 숨겨질 수 있기 때문에 public일 필요가 없다.

### 어댑터 코드의 교체가 쉽다.
- 어뎁커 코드를 자체 패키지로 이동시키면 필요한 경우 하나의 어댑터를 다른 어댑터로 교체하기 쉽다.
- 간단하게 관련 아웃고잉 포트들만 새로운 어댑터 패키지에 구현하고 기존 패키지를 지우면 된다.

### DDD 개념에 직접적으로 대응시킬 수 있다.
- domain 패키지 내에서는 DDD가 제공하는 모든 도구를 이용해 우리가 원하는 어떤 도메인 모델이든 만들 수 있다.

### 의도하지 않은 패키지 구조가 생길 수도 있다.
- 패키지 구조를 유지하기 위해서 지켜할 규칙이 있다. 또한 패키지 구조가 적합하지 않아서 아키텍처-코드 갭을 넓히고 아키텍처를 반영하지 않는 패키지를 만들어야 하는 경우도 생길 수 있다.

## 의존성 주입의 역할.
- 클린 아키텍쳐의 가장 본질적인 요건은 <u><b>인커밍 / 아웃고잉</b> 어댑터에 의존성을 갖지 않는 것이다.</u>
- 인커밍 어댑터는 제어 흐름의 방향이 어뎁터와 도메인 코드 간의 의존성이 같은 방향이기에 애플리케이션이 인커밍 어댑터에 의존성을 갖지 않게 구현하는 게 쉽다.
- 아웃고잉 어댑터에 대해서는 <b>의존성 역전 원칙</b>을 이용해야 한다.
- 애플리케이션 계층에 인터페이스를 만들고, 어댑터에 해당 인터페이스를 구현한 클래스를 두면 된다.
- 육각형 아키텍쳐에서는 어댑터 인터페이스가 포트가 된다.
- 애플리케이션 계층에서는 어댑터의 기능을 실행하기 위해 이 포트 인터페이스를 호출한다.
- 포트 인터페이스를 구현한 실제 객체의 제공은 모든 계층에 의존성을 가진 <b><i>중립적인 컴포넌트</i></b>를 도입하여 초기화된 클래스를 제공한다.

