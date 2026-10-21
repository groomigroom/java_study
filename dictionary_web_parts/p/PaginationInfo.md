`org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo`는 전자정부 표준프레임워크(eGovFrame)에서 페이징(Pagination)에 필요한 정보를 관리하는 클래스입니다.

쉽게 말하면, 게시판에 게시글이 100개 있을 때 한 페이지에 10개씩 보여 주려면 현재 몇 페이지인지, 전체 페이지가 몇 개인지, 몇 번부터 몇 번까지 페이지 번호를 표시할지 등을 계산하고 관리해야 합니다. `PaginationInfo`가 바로 이런 정보를 담는 역할을 합니다.

## 1. PaginationInfo는 어떤 정보를 관리하나요?

예를 들어 게시글이 총 125개이고 한 페이지에 10개씩 표시한다고 가정해 보겠습니다.

전체 게시글

## 125개

현재 페이지

## 3페이지

페이지당 게시글

## 10개

전체 페이지 수

## 13페이지

페이지 이동 UI에 표시할 번호 예시

1

2

3

4

5

...

`PaginationInfo`는 이런 페이징 정보를 보관하고, 관련 계산 결과를 제공합니다. 실제로 DB에서 게시글을 조회하는 작업은 일반적으로 서비스와 DAO·Mapper 등의 계층에서 처리합니다.

## 2. 주요 필드와 메서드

전자정부 표준프레임워크의 일반적인 `PaginationInfo` 사용 방식은 다음과 같습니다.

| 필드 또는 메서드 | 의미 |
| --- | --- |
| `currentPageNo` | 현재 페이지 번호 |
| `recordCountPerPage` | 한 페이지에 표시할 게시글 수 |
| `pageSize` | 페이지 번호를 한 번에 몇 개 표시할지 |
| `totalRecordCount` | 전체 게시글 수 |
| `getTotalPageCount()` | 전체 페이지 수 계산 |
| `getFirstRecordIndex()` | DB 조회에 사용할 첫 레코드 인덱스 |
| `getLastRecordIndex()` | DB 조회에 사용할 마지막 레코드 인덱스 |
| `getFirstPageNoOnPageList()` | 페이지 번호 목록의 시작 번호 |
| `getLastPageNoOnPageList()` | 페이지 번호 목록의 마지막 번호 |

정확한 필드와 메서드는 사용하는 전자정부 프레임워크 버전의 소스 코드를 기준으로 확인하면 됩니다.

## 3. 실제 코드에서는 어떻게 사용하나요?

예를 들어 컨트롤러에서 다음과 같이 사용할 수 있습니다.

java
```
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

PaginationInfo paginationInfo = new PaginationInfo();

paginationInfo.setCurrentPageNo(3);
paginationInfo.setRecordCountPerPage(10);
paginationInfo.setPageSize(5);
paginationInfo.setTotalRecordCount(125);

int totalPages = paginationInfo.getTotalPageCount();
int firstIndex = paginationInfo.getFirstRecordIndex();
int lastIndex = paginationInfo.getLastRecordIndex();
```

각 설정의 의미는 다음과 같습니다.

- `setCurrentPageNo(3)`: 현재 3페이지를 조회합니다.
- `setRecordCountPerPage(10)`: 페이지당 게시글 10개를 설정합니다.
- `setPageSize(5)`: 페이지 번호를 한 번에 5개씩 표시하도록 설정합니다.
- `setTotalRecordCount(125)`: 전체 게시글 수는 125개입니다.

이 경우 전체 페이지 수는 13페이지입니다. 실제 조회 인덱스는 전자정부 프레임워크의 인덱스 규칙을 따르며, 일반적으로 첫 레코드 인덱스는 0부터 시작합니다.

## 4. 앞서 살펴본 `AbstractKrdsPaginationRenderer`와는 어떤 관계인가요?

이 두 클래스의 역할을 구분하면 이해하기 쉽습니다.

PaginationInfo

현재 페이지, 전체 건수, 페이지 크기 등 페이징 데이터 관리

AbstractKrdsPaginationRenderer

페이징 정보를 이용해 페이지 번호와 이동 링크 등의 HTML 생성

웹 브라우저

사용자에게 페이지 이동 UI 표시

앞서 공유하신 코드에서도 이 관계를 확인할 수 있습니다.

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

여기서 `paginationInfo`는 렌더러에게 전달하는 페이징 데이터이고, `super.renderPagination(...)`은 부모 클래스의 렌더링 로직을 호출합니다.

즉, `PaginationInfo`가 페이지 정보를 제공하면 `AbstractKrdsPaginationRenderer`가 그 정보를 바탕으로 페이지 이동 HTML을 만들어 주는 구조입니다.

한 문장으로 정리하면: `PaginationInfo`는 페이지네이션에 필요한 숫자와 계산 정보를 관리하는 클래스이고, `AbstractKrdsPaginationRenderer`는 그 정보를 사용해 화면에 표시할 페이지 이동 UI를 생성하는 클래스입니다.
