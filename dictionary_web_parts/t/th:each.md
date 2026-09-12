자바 스프링 부트에서 넘겨준 Page<Question> 객체를 타임리프(Thymeleaf)의 th:each로 반복 돌릴 때, question과 loop에는 다음과 같은 값이 들어갑니다.
## 1. question (반복 객체 변수)
현재 반복 순서에 해당하는 Question 엔티티(또는 DTO) 객체 하나가 담깁니다.

* paging이 Page<Question> 타입이므로, 타임리프는 내부적으로 Page가 가지고 있는 실제 데이터 리스트(List<Question>)를 꺼내어 순해합니다.
* 루프가 돌 때마다 question.subject, question.content와 같이 해당 질문 객체의 필드에 직접 접근할 수 있습니다.

## 2. loop (반복 상태 변수)
타임리프가 제공하는 루프의 상태 정보(Loop Status Object)가 담깁니다. 현재 몇 번째 반복인지, 처음인지 마지막인지 등의 메타데이터를 확인할 수 있어 주로 번호 출력이나 조건부 스타일에 사용됩니다.
loop 변수를 통해 사용할 수 있는 주요 속성은 다음과 같습니다.

| 속성명 | 설명 |
|---|---|
| loop.index | 0부터 시작하는 현재 인덱스 번호 (0, 1, 2, ...) |
| loop.count | 1부터 시작하는 현재 순서 번호 (1, 2, 3, ...) |
| loop.size | 전체 요소의 개수 (현재 페이지에 담긴 데이터 개수) |
| loop.current | 현재 반복하고 있는 객체 자체 (question과 동일) |
| loop.even | 현재 순서가 짝수인지 여부 (boolean) |
| loop.odd | 현재 순서가 홀수인지 여부 (boolean) |
| loop.first | 현재가 첫 번째 요소인지 여부 (boolean) |
| loop.last | 현재가 마지막 요소인지 여부 (boolean) |

------------------------------
## 💡 실전 활용 예시
보통 게시판에서 번호를 출력하거나 첫 번째 항목에만 특정 CSS 클래스를 줄 때 아래처럼 사용합니다.

<tr th:each="question, loop : ${paging}">
    <!-- 1부터 시작하는 번호 출력 -->
    <td th:text="${loop.count}">1</td>
    
    <!-- 질문 제목 출력 -->
    <td>
        <a th:href="@{|/question/detail/${question.id}|}" th:text="${question.subject}">질문 제목</a>
    </td>
</tr>

만약 전체 게시글 수와 페이지 번호를 조합해서 고유한 글 번호를 역순으로 계산하고 싶다면 아래와 같은 공식으로 loop를 활용하기도 합니다.

* th:text="${paging.totalElements - (paging.number * paging.size) - loop.index}"

추가적으로 타임리프 페이징 버튼 구현이나 글 번호 역순 정렬 공식이 필요하시면 말씀해 주세요. 관련 코드를 바로 작성해 드리겠습니다!

