전자정부프레임워크(eGovFrame)에서 `org.springframework.transaction.interceptor.TransactionAttribute`는 트랜잭션을 어떻게 처리할지에 대한 설정 정보를 정의하는 Spring 인터페이스입니다.

쉽게 말하면, 메서드가 실행될 때 트랜잭션을 새로 시작할지, 기존 트랜잭션에 참여할지, 어떤 예외가 발생하면 롤백할지 등의 규칙을 나타냅니다.

## 1. 어떤 역할을 하나요?

`TransactionAttribute`는 Spring의 트랜잭션 처리 기능에 속하며, 전자정부프레임워크에서도 Spring 기반 트랜잭션 관리에 사용됩니다.

주요 역할은 다음과 같습니다.

- 트랜잭션 전파(Propagation): 기존 트랜잭션이 있을 때 참여할지, 새로 시작할지 결정
- 격리 수준(Isolation): 동시에 실행되는 트랜잭션 간 데이터 접근 수준 설정
- 읽기 전용(Read-only): 조회 중심 트랜잭션인지 여부 지정
- 롤백 규칙(Rollback Rules): 어떤 예외가 발생했을 때 롤백할지 결정
- 트랜잭션 제한 시간(Timeout): 트랜잭션의 최대 실행 시간 설정

## 2. 코드에서는 어떻게 사용하나요?

예를 들어 다음과 같은 코드가 있다고 가정하겠습니다.

java
```
import org.springframework.transaction.interceptor.TransactionAttribute;

public void execute() {
    TransactionAttribute txAttribute = ...;
}
```

`TransactionAttribute`는 인터페이스이므로 일반적으로 직접 생성하는 것이 아니라, Spring의 트랜잭션 설정이나 트랜잭션 속성 소스 등을 통해 제공받습니다.

이 인터페이스에서 제공하는 주요 메서드는 다음과 같습니다.

| 메서드 | 의미 |
| --- | --- |
| `getPropagationBehavior()` | 트랜잭션 전파 방식 조회 |
| `getIsolationLevel()` | 트랜잭션 격리 수준 조회 |
| `getTimeout()` | 트랜잭션 제한 시간 조회 |
| `isReadOnly()` | 읽기 전용 여부 조회 |
| `rollbackOn(Throwable ex)` | 해당 예외 발생 시 롤백할지 판단 |

## 3. 전자정부프레임워크에서는 어디에 연결되나요?

전자정부프레임워크 프로젝트에서 다음과 같은 설정을 사용하는 경우를 생각해 볼 수 있습니다.

java
```
@Transactional
public void saveData() {
    // 데이터 저장 작업
}
```

또는 XML에서 트랜잭션을 설정할 수도 있습니다.

xml
```
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <tx:method name="select*" read-only="true"/>
        <tx:method name="*" rollback-for="Exception"/>
    </tx:attributes>
</tx:advice>
```

이런 설정에서 지정한 트랜잭션 속성은 Spring의 트랜잭션 처리 과정에서 해석되어 `TransactionAttribute` 형태로 사용될 수 있습니다.

예를 들어 위 설정은 다음과 같은 의미입니다.

- `select`로 시작하는 메서드는 읽기 전용 트랜잭션으로 처리합니다.
- 그 외 메서드는 `Exception`이 발생하면 롤백하도록 설정합니다.

단, 실제 동작은 트랜잭션 매니저, AOP 적용 여부, Spring 버전 및 구체적인 설정에 따라 달라질 수 있습니다.

## 4. `TransactionAttribute`와 `@Transactional`의 차이

둘은 같은 것이 아닙니다.

- `@Transactional`: 개발자가 메서드나 클래스에 트랜잭션 속성을 지정하는 애너테이션입니다.
- `TransactionAttribute`: Spring 내부에서 트랜잭션 속성을 표현하고 처리하기 위해 사용하는 인터페이스입니다.

즉, `@Transactional`이나 XML 설정에 정의된 내용이 Spring 내부에서 `TransactionAttribute`로 표현되어 트랜잭션 처리에 활용될 수 있습니다.

정리하면, 전자정부프레임워크에서 `org.springframework.transaction.interceptor.TransactionAttribute`를 보셨다면, 대부분 Spring의 트랜잭션 관리 로직과 관련된 부분입니다. 전자정부프레임워크 전용 클래스가 아니라 Spring에서 제공하는 인터페이스입니다.

혹시 이 클래스가 컴파일 오류, 라이브러리 누락 오류, 소스 코드 또는 디버깅 과정 중에 나타난 것인가요? 어떤 상황에서 보셨는지 알려주시면 해당 상황에 맞게 설명해 드리겠습니다.
