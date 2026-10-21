`org.egovframe.rte.ptl.mvc.tags.ui.pagination.AbstractKrdsPaginationRenderer`는 스프링 부트 자체의 클래스가 아니라, 전자정부 표준프레임워크(eGovFrame)에서 KRDS 기반 페이지네이션 UI를 렌더링하기 위한 추상 클래스입니다.

쉽게 말하면, 게시판이나 검색 결과에서 다음과 같은 페이지 이동 영역을 HTML로 만들어 주는 기능과 관련이 있습니다.

--------

`AbstractKrdsPaginationRenderer`에서 선언했기 때문입니다.

이것이 바로 Java의 상속(Inheritance) 개념입니다.

## 1. 현재 코드의 상속 관계

작성하신 코드에서 이 부분이 핵심입니다.

java
```
public class EgovKrdsPaginationRenderer
        extends AbstractKrdsPaginationRenderer {
```

`extends`는 부모 클래스의 기능과 멤버를 자식 클래스가 물려받는다는 의미입니다.

### 부모 클래스

AbstractKrdsPaginationRenderer

java
```
protected String previousPageLabel;
protected String nextPageLabel;
protected String firstPageLabel;
protected String currentPageLabel;


```
java
```

//아래와 같은 내용들도 미리 선언되어서 대입해서 사용 가능함.
previousPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi prev\" href=\"#\">이전</a>";
		previousPageDisabledLabel = "<span class=\"page-navi prev disabled\">이전</span>";
	    firstPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
	    currentPageLabel = "<a class=\"page-link active\" href=\"#\">{0}</a>";
	    otherPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
	    nextPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi next\" href=\"#\">다음</a>";
		nextPageDisabledLabel = "<span class=\"page-navi next disabled\">다음</span>";
	    lastPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
	    dotPageLabel = "<span class=\"page-link link-dot\">...</span>";	
```

----------------------

# renderPagination()

`AbstractKrdsPaginationRenderer`의 `renderPagination()` 메서드는 페이징 정보(`PaginationInfo`)를 바탕으로 페이지 번호와 이전·다음 버튼 등의 HTML 문자열을 생성하는 메서드입니다.

앞서 작성하신 `EgovKrdsPaginationRenderer` 코드와 연결해서 살펴보겠습니다.

## 1. 메서드 이름의 의미

`renderPagination`을 단어별로 나누면 다음과 같습니다.

- `render`: 화면에 표시할 형태로 만들다, 렌더링하다.
- `Pagination`: 페이지 이동 기능, 즉 페이지네이션.

따라서 `renderPagination()`은 페이지 이동 UI를 구성하는 HTML을 만드는 기능이라고 이해하면 됩니다.

여기서 주의할 점은 HTML을 생성하는 것과 브라우저에 직접 화면을 출력하는 것은 다르다는 것입니다. 공유하신 코드에서는 반환 타입이 `String`이므로 HTML 문자열을 반환합니다.

## 2. 메서드의 매개변수는 무엇인가요?

앞서 공유하신 코드에 다음과 같이 작성되어 있습니다.

java
```
@Override
public String renderPagination(
        PaginationInfo paginationInfo,
        String jsFunction) {

    updateLabelsWithCurrentLocale();

    return super.renderPagination(paginationInfo, jsFunction);
}
```

각 부분의 의미는 다음과 같습니다.

| 코드 | 의미 |
| --- | --- |
| `String` | HTML 문자열을 반환한다는 의미 |
| `renderPagination` | 메서드 이름 |
| `PaginationInfo paginationInfo` | 현재 페이지, 전체 게시글 수 등 페이징 정보 |
| `String jsFunction` | 페이지 이동 시 호출할 JavaScript 함수명 또는 함수 표현식 |
| `@Override` | 부모 클래스의 메서드를 재정의한다는 의미 |
| `super.renderPagination(...)` | 부모 클래스의 실제 렌더링 메서드 호출 |

`jsFunction`에 전달되는 구체적인 값과 사용 방식은 호출부 및 부모 클래스의 구현을 확인해야 정확히 알 수 있습니다.

## 3. 실제로 어떻게 동작하나요?

예를 들어 게시글이 100개이고 현재 3페이지라고 가정해 보겠습니다.

① 입력 데이터: PaginationInfo

- 현재 페이지: 3
- 전체 게시글: 100개
- 페이지당 게시글: 10개
- 페이지 번호 표시 개수: 10개

② renderPagination() 실행

- 이전 페이지 링크 생성
- 페이지 번호 링크 생성
- 현재 페이지 표시
- 다음 페이지 링크 생성

③ 반환되는 HTML의 예시

HTML줄 바꿈코드 복사미리보기

```
<a class="page-navi prev" href="#">이전</a>
<a class="page-link" href="#">1</a>
<a class="page-link" href="#">2</a>
<a class="page-link active" href="#">3</a>
<a class="page-link" href="#">4</a>
<a class="page-navi next" href="#">다음</a>
```

이 HTML은 이해를 돕기 위한 예시이며 실제 반환 결과는 구현과 페이징 설정에 따라 다릅니다.

## 4. 왜 `super.renderPagination()`을 호출하나요?

이 부분이 핵심입니다.

java
```
return super.renderPagination(paginationInfo, jsFunction);
```

`super`는 부모 클래스를 가리킵니다.

현재 클래스인 `EgovKrdsPaginationRenderer`는 부모 클래스인 `AbstractKrdsPaginationRenderer`를 상속하고 있습니다. 따라서 부모 클래스가 이미 구현한 페이징 HTML 생성 로직을 재사용할 수 있습니다.

현재 코드의 실행 순서는 다음과 같습니다.

1. `updateLabelsWithCurrentLocale()`로 현재 언어에 맞는 이전·다음 문구를 설정합니다.
2. `super.renderPagination(...)`을 호출합니다.
3. 부모 클래스가 페이징 정보와 HTML 템플릿을 사용해 HTML 문자열을 생성합니다.
4. 생성된 문자열을 `return`으로 반환합니다.

즉, 자식 클래스는 다국어 문구를 설정하는 역할을 추가하고, 실제 페이징 렌더링은 부모 클래스의 구현을 활용하는 구조입니다.

## 5. `renderPagination()`과 `PaginationInfo`의 차이

| 구분 | 역할 |
| --- | --- |
| `PaginationInfo` | 페이지 번호, 전체 건수 등 페이징 정보를 관리 |
| `renderPagination()` | 페이징 정보를 바탕으로 HTML 문자열 생성 |
| `EgovKrdsPaginationRenderer` | 다국어 문구 설정 등 렌더링 동작을 확장 |
| 브라우저 | 전달받은 HTML을 실제 화면에 표시 |

참고로 `renderPagination()` 자체가 데이터베이스에서 게시글을 조회하는 것은 아닙니다. 게시글 조회는 일반적으로 서비스와 Mapper 등의 계층에서 처리하고, 이 메서드는 페이지 이동 UI를 만드는 데 집중합니다.

한 문장으로 정리하면, `renderPagination()`은 `PaginationInfo`를 받아 페이지 이동 버튼과 번호를 HTML로 만들어 반환하는 메서드입니다.
