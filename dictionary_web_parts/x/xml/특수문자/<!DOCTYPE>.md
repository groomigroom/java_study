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
