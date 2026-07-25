import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
      ArrayList<Integer> list = new ArrayList<>();

      list.add(1);
      list.add(2);
      list.add(3);

      System.out.println(list.get(1));
    }
}


자바 ArrayList 문법    +7                    자바의 ArrayList는 크기가 자동으로 조절되는 동적 배열 자료구조이며 선언, 추가, 조회, 삭제 메서드가 핵심입니다. 자세한 문법은 다음과 같습니다.선언 및 생성import java.util.ArrayList; 코드로 패키지를 먼저 불러옵니다.기본형: ArrayList<데이터타입> 리스트이름 = new ArrayList<>();예시: ArrayList<String> list = new ArrayList<>();주요 메서드값 추가 (add): list.add("사과"); (맨 뒤에 추가), list.add(0, "바나나"); (특정 인덱스에 추가)값 조회 (get): list.get(0); (특정 인덱스의 값 가져오기)값 수정 (set): list.set(0, "포도"); (특정 인덱스의 값을 변경)값 삭제 (remove): list.remove(0); (특정 인덱스 삭제), list.remove("사과"); (값으로 삭제)크기 확인 (size): list.size() (저장된 데이터 개수 반환)포함 여부 (contains): list.contains("사과"); (값이 있으면 true, 없으면 false)전체 삭제 (clear): list.clear(); (모든 요소 삭제)만약 원하시는 경우:예제 코드 전체 보기기본 배열(Array)과의 차이점 비교반복문(for-each) 사용법어떤 내용을 더 알려드릴까요?Inpa Dev 👨‍💻자바 ArrayList 구조 & 사용법 정리 - Inpa Dev ‍ - 티스토리2023. 1. 31. — ArrayList 생성 문법을 보면 꺾쇠 괄호 <> 기호를 이용해 타입을 지정함을 볼 수 있다. 저 꺾쇠 괄호가 바로 제네릭Visit Website이다. 만일 꺾...티스토리[Java/문법] ArrayLIst 주요 메서드 정리 (add, get, contains, indexOf ...2025. 2. 12. — 티스토리 · ✏️ 추가: add · ✏️ 조회: get, contains · ✏️ 수정: set · ✏️ 삭제: remove · ✏️ 크기 및 확인 · ✏️ 변환...티스토리[Java] ArrayList 사용방법과 꼭 알아야 할 주의사항 (개념, 특징, 메소드 및 예제)2026. 2. 1. — 01. ArrayList란 무엇인가? · ✓ 데이터 순서 유지 · ✓ 중복 데이터 허용 · ✓ 인덱스 기반의 빠른 접근 가능 · ✓ 크기 자동 증가(동적 배열) ...인천일보아카데미[Java 기초] ArrayList를 다루는 함수들(add, set, get, remove, size) | 인천 ...2023. 8. 30. — add는 리스트에 값을 넣어줄 때 사용한다. 값만 입력하면 맨 뒤에 순서대로 들어가고 인덱스를 입력하면 해당 인덱스에 저장된다. get는 리스트에 저장된 값을 인...티스토리자바에서 Array와 ArrayList차이점 - 기본을 알면 개발이 쉬워진다2023. 5. 1. — Array는 인덱스를 사용하여 직접 변경, ArrayList는 set 메서드 사용합니다. Array는 고정 길이 이므로 삭제를 할 수 없고 ArrayList는 r...Medium·검은헤드셋[JAVA] learn about ArrayList - Medium2025. 11. 25. — ArrayList 생성 : ArrayList fruits = new ArrayList ; 요소가져오기 (listName.get) String first ; 요소 ...티스토리[자료구조] ArrayList 란? - 코드비의 개발자되기 - 티스토리2024. 9. 3. — 1. ArrayList란? 배열의 단점을 보완하여, 크기가 자동으로 조정되며 자바에서 가장 많이 사용되는 동적배열 기반의 자료구조 입니다. ArrayList 는 ...티스토리[Java] ArrayList 클래스 사용하기2022. 9. 19. — ArrayList를 사용하는 기본 형식이다.GitHub자바에서 new ArrayList<>(); 의 <>와 ()는 무엇일까?2024. 4. 18. — List list = new ArrayList(); list. add( 123); String str = list. get( 0); // 컴파일 에러가 아닌 런타...    AI 답변에 오류가 있을 수 있습니다. 자세히 알아보기