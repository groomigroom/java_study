`org.springframework.web.bind.annotation.ModelAttribute`는 Spring Boot에서 HTTP 요청으로 전달된 파라미터를 자바 객체에 바인딩하거나, Controller에서 사용할 객체를 Model에 등록할 때 사용하는 어노테이션입니다.

주로 검색 조건, 게시판 목록 조회 조건, 페이징 정보 등을 처리할 때 사용합니다.

## 1. `@ModelAttribute`의 기본 의미

java
```
import org.springframework.web.bind.annotation.ModelAttribute;
```

`@ModelAttribute`는 사용 위치에 따라 크게 두 가지 역할을 합니다.

- 메서드 파라미터에 사용: 요청 파라미터를 자바 객체에 바인딩합니다.
- 메서드에 사용: 해당 메서드가 반환한 객체를 Model에 등록합니다.

질문하신 `@ModelAttribute("searchVO")`는 주로 첫 번째 방식에서 사용하며, 요청 파라미터를 객체에 바인딩하고 해당 객체를 `searchVO`라는 이름으로 Model에도 노출합니다.

## 2. `@ModelAttribute("searchVO")`의 의미

다음 코드를 살펴보겠습니다.

java
```
@ModelAttribute("searchVO") SearchVO searchVO
```

각 부분의 의미는 다음과 같습니다.

| 구성 요소 | 의미 |
| --- | --- |
| `@ModelAttribute` | 요청 파라미터를 객체에 바인딩 |
| `"searchVO"` | Model에 등록할 속성 이름 |
| `SearchVO` | 요청 데이터를 담을 자바 클래스 |
| `searchVO` | Controller 메서드에서 사용하는 변수명 |

즉, 요청으로 전달된 검색 조건을 `SearchVO` 객체에 담고, 그 객체를 `searchVO`라는 이름으로 사용할 수 있게 하는 것입니다.

## 3. 실제 사용 예시

### ① 검색 조건을 담는 VO 클래스

java
```
public class SearchVO {

    private String searchKeyword;
    private int pageIndex;

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
```

이 클래스는 검색어와 페이지 번호를 담는 객체입니다.

### ② Controller에서 사용

java
```
@GetMapping("/accelerators")
public String selectAcceleratorList(
        @ModelAttribute("searchVO") SearchVO searchVO,
        Model model) {

    String keyword = searchVO.getSearchKeyword();
    int pageIndex = searchVO.getPageIndex();

    // 검색 조건을 이용한 목록 조회 처리

    return "accelerator/list";
}
```

사용자가 다음 URL로 요청했다고 가정하겠습니다.

text
```
GET /accelerators?searchKeyword=sensor&pageIndex=2
```

Spring은 요청 파라미터를 읽어 `SearchVO` 객체에 자동으로 바인딩합니다.

HTTP 요청

`searchKeyword=sensor`

`pageIndex=2`

Spring의 데이터 바인딩

요청 파라미터 → SearchVO

searchVO 객체

searchKeyword

sensor

pageIndex

2

이렇게 하면 `searchVO.getSearchKeyword()`는 `"sensor"`를, `searchVO.getPageIndex()`는 `2`를 반환합니다.

단, 요청에 파라미터가 없으면 객체의 기본값이 사용되며, 자료형 변환에 실패하면 바인딩 오류가 발생할 수 있습니다.

## 4. 왜 `"searchVO"`라는 이름을 지정하나요?

`"searchVO"`는 단순한 문자열이 아니라 Spring Model에서 객체를 식별하는 이름으로 사용됩니다.

예를 들어 Controller가 다음과 같이 작성되어 있다면,

java
```
@ModelAttribute("searchVO") SearchVO searchVO
```

JSP에서는 다음과 같이 객체에 접근할 수 있습니다.

jsp
```
${searchVO.searchKeyword}
${searchVO.pageIndex}
```

즉, `searchVO`라는 이름으로 등록된 객체의 속성에 접근하는 것입니다.

Thymeleaf를 사용한다면 다음과 같은 방식으로 활용할 수도 있습니다.

HTML줄 바꿈코드 복사미리보기

```
<form th:object="${searchVO}" method="get">
    <input type="text" th:field="*{searchKeyword}">
    <input type="number" th:field="*{pageIndex}">
    <button type="submit">검색</button>
</form>
```

따라서 `"searchVO"`는 주로 Controller와 화면 사이에서 데이터를 주고받을 때 사용하는 Model 속성 이름입니다.

## 5. `@ModelAttribute`를 생략하면 어떻게 되나요?

다음 두 코드를 비교해 보겠습니다.

java
```
public String selectList(
        @ModelAttribute("searchVO") SearchVO searchVO) {
    // ...
}
```

java
```
public String selectList(SearchVO searchVO) {
    // ...
}
```

일반적인 Spring MVC 환경에서는 두 코드 모두 복합 객체에 요청 파라미터를 바인딩할 수 있습니다.

다만 차이가 있습니다.

- `@ModelAttribute("searchVO")`를 사용하면 Model 속성 이름을 `searchVO`로 명시합니다.
- 생략하면 Spring이 일반적으로 클래스 이름을 기반으로 속성 이름을 추론합니다. 이 경우 `SearchVO`는 보통 `searchVO`라는 이름을 사용합니다.

따라서 속성 이름을 명확히 지정하거나 화면에서 동일한 이름으로 객체를 참조해야 할 때 `@ModelAttribute("searchVO")`를 사용하는 것이 좋습니다.

## 6. 메서드에 붙이는 `@ModelAttribute`는 다른 용도입니다

파라미터가 아니라 메서드 자체에 붙이면 의미가 달라집니다.

java
```
@ModelAttribute("searchVO")
public SearchVO createSearchVO() {
    return new SearchVO();
}
```

이 경우 메서드가 반환한 객체를 Model의 `searchVO` 속성으로 등록합니다. 일반적으로 해당 Controller의 요청 처리 전에 실행되어 화면이나 다른 핸들러 메서드에서 사용할 수 있습니다.

이는 요청 파라미터를 객체에 바인딩하는 메서드 파라미터 방식과 구별해야 합니다.

## 핵심 정리

- `@ModelAttribute` : 요청 파라미터를 자바 객체에 바인딩하거나 Model에 객체를 등록하는 기능입니다.
- `@ModelAttribute("searchVO")` : 객체를 바인딩하면서 Model 속성 이름을 `searchVO`로 명시합니다.
- `SearchVO` : 검색 조건 등의 데이터를 담는 객체입니다.
- `searchVO` : Controller 코드에서 사용하는 변수명이자, 지정한 경우 화면에서 참조할 Model 속성 이름입니다.

전자정부 표준프레임워크 프로젝트에서 이 코드를 발견했다면, 대부분 검색 조건이나 페이징 조건을 `VO` 객체로 한꺼번에 전달받아 목록 조회에 사용하는 패턴이라고 이해하면 됩니다.
