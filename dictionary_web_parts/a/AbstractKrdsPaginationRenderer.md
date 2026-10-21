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
