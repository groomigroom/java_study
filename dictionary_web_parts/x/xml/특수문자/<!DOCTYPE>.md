전자정부프레임워크(eGovFrame)에서 다음 코드는 MyBatis Mapper XML 파일의 문서 유형(DTD)을 선언하는 부분이야.

```
<!DOCTYPE mapper PUBLIC
"-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```

쉽게 말하면, 이 XML 파일이 MyBatis Mapper 규칙에 맞게 작성되었는지 확인하기 위한 선언이야.

### 1. 각 부분의 의미

| 코드                  | 의미                        |
| ------------------- | ------------------------- |
| `<!DOCTYPE mapper>` | 이 XML 문서의 유형이 mapper임을 선언 |
| `PUBLIC`            | 공개적으로 정의된 DTD를 사용한다는 의미   |
| `mybatis.org`       | MyBatis에서 제공하는 규칙         |
| `DTD 3.0`           | MyBatis Mapper XML 3.0 규칙 |
| URL                 | DTD 파일의 위치                |

### 2. DTD란?

DTD(Document Type Definition)는 XML 문서가 지켜야 할 작성 규칙을 정의한 파일이야.

예를 들어 다음과 같은 규칙을 검사할 수 있어.

- XML에서 사용할 수 있는 태그
- 태그의 작성 순서
- 속성의 사용 방법
- 태그 내부에 들어갈 수 있는 요소

### 3. 전자정부프레임워크에서 사용하는 이유

전자정부프레임워크는 MyBatis를 이용해 데이터베이스와 연동할 수 있어.

Mapper XML 파일에서 SQL을 작성하고 실행하는데, 이때 MyBatis가 정해 놓은 XML 규칙을 따라야 해.

예를 들어 다음과 같이 작성해.

```
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper PUBLIC
    "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="userDAO">

    <select id="selectUser" resultType="map">
        SELECT * FROM users
    </select>

</mapper>
```

각 요소의 역할은 다음과 같아.

- `mapper`: SQL을 정의하는 최상위 태그
- `namespace`: Mapper의 이름 또는 식별자
- `select`: 조회 SQL 작성
- `id`: SQL 구문을 구분하는 이름
- `resultType`: 조회 결과를 반환할 데이터 타입

### 4. 꼭 기억해야 할 핵심

한 줄 요약

`<!DOCTYPE mapper ...>`는 MyBatis Mapper XML 파일이 따라야 할 문법과 구조를 정의하는 DTD 선언이다.

주의할 점

- SQL을 실행하는 코드가 아님.
- 데이터베이스 연결을 설정하는 코드가 아님.
- XML 문서의 구조와 작성 규칙을 지정하는 코드임.

전자정부프레임워크에서 개발할 때는 이 선언을 Mapper XML 파일 상단에 작성하는 기본 구조라고 이해하면 돼.

-------------------------------
# configuration

전자정부표준프레임워크(eGovFrame)에서 XML 파일 맨 위에 작성하는

```
<!DOCTYPE configuration PUBLIC
    "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
```

이 코드는 \*\*XML 문서의 구조와 작성 규칙을 지정하는 선언문(DTD)\*\*이야.

쉽게 말하면, XML 파일을 작성할 때 어떤 태그를 사용할 수 있고, 태그를 어떤 구조로 작성해야 하는지 알려주는 역할을 해.

## 1. 각 부분의 의미

| 코드                                            | 의미                           |
| --------------------------------------------- | ---------------------------- |
| `<!DOCTYPE`                                   | 문서의 유형을 선언                   |
| `configuration`                               | XML의 최상위 루트 태그 이름            |
| `PUBLIC`                                      | 외부에서 공개적으로 정의된 DTD를 사용한다는 의미 |
| `-//mybatis.org//DTD Config 3.0//EN`          | MyBatis가 제공하는 DTD의 식별자       |
| `http://mybatis.org/dtd/mybatis-3-config.dtd` | DTD 파일의 위치                   |

## 2. 전자정부표준프레임워크에서 사용하는 이유

전자정부표준프레임워크는 MyBatis를 이용해 데이터베이스와 연동할 수 있어.

MyBatis 설정 XML 파일에서는 위 선언문을 통해 MyBatis가 정해 놓은 XML 작성 규칙을 적용해.

예를 들어 다음과 같은 구조를 사용할 수 있어.

```
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE configuration PUBLIC
    "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>

    <settings>
        <setting name="cacheEnabled" value="true"/>
    </settings>

    <typeAliases>
        <typeAlias alias="User" type="com.example.User"/>
    </typeAliases>

</configuration>
```

여기서 각 태그의 역할은 다음과 같아.

- `<configuration>` : MyBatis 전체 설정을 감싸는 최상위 태그
- `<settings>` : MyBatis의 동작 설정
- `<typeAliases>` : 자바 클래스의 별칭 설정
- `<typeAlias>` : 특정 클래스에 별칭 지정

## 3. 꼭 알아야 할 핵심 개념

헷갈리기 쉬운 부분을 정리해 줄게.

| 구분        | 역할                       |
| --------- | ------------------------ |
| XML       | 데이터를 표현하는 문서 형식          |
| DTD       | XML 문서의 구조와 규칙 정의        |
| MyBatis   | SQL과 자바 프로그램을 연결하는 프레임워크 |
| eGovFrame | 전자정부 표준 개발 환경 및 프레임워크    |

한 줄 요약

`DOCTYPE` 선언은 이 XML 파일이 MyBatis 3.0 설정 규칙을 따르는 문서라는 것을 알려주는 역할이야.

참고로 이 선언문 자체가 MyBatis를 실행하거나 데이터베이스에 연결하는 코드는 아니야. XML 문서의 유효성을 검사하고 올바른 구조로 작성하도록 돕는 선언이야.
