# 패키지 구조

## 계층으로 구성하기

--- 

buckpal <br/>
├--- domain <br/>
⏐&nbsp;&nbsp;&nbsp;├--- Account <br/>
⏐&nbsp;&nbsp;&nbsp;├--- Activity <br/>
⏐&nbsp;&nbsp;&nbsp;├--- AccountRepository <br/>
⏐&nbsp;&nbsp;&nbsp;├--- AccountService <br/>
├--- persistence <br/>
⏐&nbsp;&nbsp;&nbsp;├--- AccountRepositoryImpl <br/>
├--- web <br/>
⏐&nbsp;&nbsp;&nbsp;├--- AccountController <br/>


#### 의존성 역전 원칙을 적용
 **Domain** 패키지에 AccountRepository 인터페이스두고 **persistence** 패키지에 AccountRepositoryImpl 구현체를 두어서 의존성을 역전 시켰다.  

#### 해당 구조의 문제점.
1. 애플리케이션의 기능 조각이나 특성을 구분 짓는 패키지 경계가 없다.
   - 새로운 기능을 추가해야한다면 web 패키지에 controller, domain 패키지에 service,repository,entity를 추가해야하고 persistence 패키지에 레포지토리 구현체 클래스를 추가해야한다.
   - 추가적인 구조가 없으면 예상하지 못한 부수효과를 일으킬 수 있는 클래스들의 묶음으로 변모할 가능성이 높아진다.
2. 애플리케이션이 어떤 유스케이스들을 제공하는 지 파악할 수 없다.
   - AccountService와 AccountController 만으로는 어떤 유스케이스인지 파악이 어렵다. 어떤 서비스가 어떤 기능을 구현했는 지 추측해야하고, 해당 서비스 내 어떤 메소드가 그에 대한 책임을 수행하는 지 찾아야한다.
3. 패키지 구조를 통해서 우리가 목표로 하는 아키텍쳐를 파악할 수 없다.
   - 육각형 아키텍쳐 스타일을 따랐다고 추측하기 어렵다.
   - 영속성 어뎁터가 도메인 계층에 어떤 기능을 제공하는 지 확인이 어렵다.
   - 인커밍 포트와 아웃고잉 포트가 코드 속에 슴겨져있다.
