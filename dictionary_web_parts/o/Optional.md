import java.util.Optional;

자바 스프링 부트에서 Optional은 "값이 비어있을 수도 있고, 존재할 수도 있는 상태"를 나타내는 컨테이너 객체입니다.
자바 개발자들의 영원한 숙적인 NullPointerException(NPE)을 원천 차단하고, 값이 없을 때의 예외 처리를 깔끔하고 안전하게 구현하기 위해 사용합니다.
------------------------------
## 💡 Optional의 핵심 의미와 사용 목적
스프링 부트에서는 주로 데이터베이스에서 데이터를 조회하는 Repository(JPA) 레이어에서 가장 많이 마주치게 됩니다.

* Null 안전성: 데이터가 없을 때 null을 반환하는 대신 빈 상자인 Optional.empty()를 반환하여 코드가 튕기는 현상(NPE)을 막아줍니다.
* 직관적인 예외 처리: "값이 없으면 에러를 던지거나 기본값을 반환해라"라는 로직을 단 한 줄로 코딩할 수 있게 해줍니다.

## 🔍 실무 적용 예시 (JPA 조회 시)

// 데이터가 없을 수도 있으므로 Optional<Member> 타입으로 반환됩니다.
Optional<Member> optionalMember = memberRepository.findById(1L);
// 1. 값이 없으면 바로 예외를 던지는 방식 (가장 많이 씀)
Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
// 2. 값이 없으면 새로운 객체나 기본값을 리턴하는 방식
Member defaultMember = optionalMember.orElse(new Member("기본 사용자"));

------------------------------
## 📦 속해있는 Start Dependency?
결론부터 말씀드리면, Optional은 스프링 부트 스타터 의존성(Start Dependency)이 전혀 필요 없습니다.
Optional은 스프링 프레임워크나 외부 라이브러리 기능이 아니라, 자바 8(Java 8)부터 자바 언어 자체에 내장된 순수 표준 API이기 때문입니다. 패키지 경로도 java.util.Optional입니다. [1] 
따라서 스프링 부트 프로젝트라면 아무런 추가 설정 없이 곧바로 사용할 수 있습니다. [2] 
------------------------------
## 🛠️ 자주 쓰는 핵심 메서드 3가지

   1. orElseThrow(): 값이 없으면 지정한 예외(익명 함수나 커스텀 예외)를 발생시킵니다.
   2. ifPresent(): 값이 존재할 때만 특정 비즈니스 로직(람다식)을 실행합니다.
   3. isPresent(): 값이 들어있는지 여부를 true/false로 확인합니다. (다만, 이 메서드를 통한 if문 분기는 옛날 방식이므로 orElseThrow나 ifPresent 사용을 더 권장합니다.) [3, 4, 5] 

현재 작성 중인 코드에서 findById() 등으로 데이터를 가져온 후 .get()을 썼다가 노란색 경고가 뜨거나, 비어있는 값 처리에 어려움을 겪고 계시나요? 에러가 나거나 고민 중이신 서비스 로직 코드를 보여주시면 가장 깔끔한 Optional 처리 문법으로 리팩토링해 드리겠습니다.


-----------------------


## .isEmpty()

---------------------


자바 스프링 부트(자바 11 버전 이상)에서 Optional 객체에 .isEmpty()를 호출하는 것은 "이 객체 안에 데이터가 비어있는지(null인지)" 확인하는 기능입니다.
가장 직관적으로 이해하자면, 기존에 사용하던 .isPresent()의 반대 기능이라고 생각하시면 됩니다.
------------------------------
## 🔍 비교로 이해하기
결과값으로 참/거짓(boolean)을 반환합니다.

* isPresent() : 데이터가 있으면 true, 없으면 false [1]
* isEmpty() : 데이터가 없으면(비어있으면) true, 있으면 false [1]

## 💻 코드로 보는 차이점
기존 자바 8에서는 값이 없을 때의 조건을 체크하려면 !optional.isPresent() 처럼 느낌표(!)를 붙여서 부정문으로 작성해야 했습니다.

Optional<SiteUser> _siteUser = userRepository.findByUsername("hong");
// 1. 기존 방식 (!isPresent)if (!_siteUser.isPresent()) {
    throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
}
// 2. 자바 11 이후 방식 (isEmpty) - 가독성이 더 좋음!if (_siteUser.isEmpty()) {
    throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
}

## 💡 왜 사용하나요?
코드에서 !(NOT 연산자)는 가끔 눈에 잘 띄지 않아 코드를 읽을 때 오해를 불러일으키기 쉽습니다. .isEmpty()를 사용하면 "비어있다면~"이라는 의미가 영어 문장처럼 직관적으로 읽히기 때문에 코드의 가독성을 높이기 위해 사용합니다.
------------------------------
현재 이 코드를 로그인 체크나 회원가입 시 중복 사용자를 검사하는 예외 처리 구문에서 사용하고 계시나요?
그렇다면 if (_siteUser.isEmpty()) 구문 내에서 어떤 예외(Exception)를 던지거나 처리할 계획인지 알려주세요. 상황에 맞는 깔끔한 예외 처리 패턴을 안내해 드리겠습니다.


## .ofNullable
```
데이터가 존재하지 않을 때 발생할 수 있는 NullPointerException(NPE)을 안전하게 방지하고, 데이터의 부재(Null)를 명확하게 표현하기 위해서입니다.
이 방식을 사용하는 핵심 이유는 다음과 같습니다.

1. NullPointerException 방지

• findByGroupNameAndMemberNumber 쿼리 메서드는 조건에 맞는 데이터가 데이터베이스에 없을 경우 null을 반환합니다.
• 만약 Optional.of(...)를 사용했다면, 반환된 값이 null일 때 그 자리에서 바로 NullPointerException이 발생합니다.
• Optional.ofNullable(...)은 값이 null이어도 예외를 던지지 않고, 비어있는 Optional 객체(Optional.empty())를 안전하게 생성해 줍니다.

2. Spring Data JPA의 기본 반환 타입 지원

사실 Spring Data JPA는 Optional을 자체적으로 지원합니다. 리포지토리(Repository)의 메서드 반환 타입을 Optional<GroupMainSecondEntity>로 직접 선언하면, 스프링이 알아서
데이터가 없을 때 Optional.empty()를 반환해 줍니다.
java
// 추천하는 방식: 리포지토리 인터페이스에서부터 Optional로 반환
Optional<GroupMainSecondEntity> findByGroupNameAndMemberNumber(String groupName, Long memberNumber);
코드를 사용할 때는 주의가 필요합니다.
현재 코드처럼 서비스 레이어에서 Optional.ofNullable로 직접 감싸고 있다면, 이는 리포지토리 메서드의 반환 타입이 일반 엔티티(GroupMainSecondEntity)로 되어 있기 때문입니다.
 null이 올 수 있는 레거시 메서드나 외부 라이브러리의 결과를 안전하게 Optional로 변환할 때 ofNullable이 필수적입니다.

3. 후속 처리의 간결함

Optional로 감싸두면 if (entity != null) 같은 지저분한 null 체크 코드 없이, 함수형 스타일로 깔끔하게 후속 처리를 할 수 있습니다.
java
// 데이터가 없으면 예외를 던지거나
GroupMainSecondEntity entity = groupMainSecondEntity
    .orElseThrow(() -> new EntityNotFoundException("그룹을 찾을 수 없습니다."));

// 데이터가 있을 때만 특정 로직을 수행
groupMainSecondEntity.ifPresent(e -> doSomething(e));
코드를 사용할 때는 주의가 필요합니다.
추가적인 코드 개선이나 Optional을 활용한 효율적인 예외 처리 패턴이 필요하시면 언제든 말씀해 주세요!

//Optional<GroupMainSecondEntity> groupMainSecondEntity = Optional.ofNullable(this.groupMainSecondRepository.findByGroupNameAndMemberNumber(groupName, memberNumber));
```
