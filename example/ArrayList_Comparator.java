import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
      ArrayList<String> list = new ArrayList<>();

      list.add("groomi");
      list.add("groomii");
      list.add("groomiiiii");

      list.sort(new Comparator<String>(){
        @Override
        public int compare(String str1, String str2) {
          return str1.compareTo(str2);
        }
      });

      list.sort((Comparator<String>) (str1, str2) -> str1.compareTo(str2));
    }
}


자바에서 Comparator의 의미    +3                    자바에서 Comparator는 두 객체를 비교하여 정렬 기준을 외부에서 따로 정의하는 인터페이스입니다. 기본 정렬 기준 외에 새로운 정렬 방식을 만들거나, 원본 코드를 고칠 수 없을 때 사용합니다.핵심 특징외부 정렬 기준: 비교할 객체 내부가 아닌 바깥에서 정렬 규칙을 따로 만듭니다.compare 메서드: 두 객체 o1, o2를 받아 첫 번째가 크면 양수, 같으면 0, 작으면 음수를 반환합니다.다양한 활용: 여러 개의 정렬 방식을 만들어 필요할 때마다 바꿔 쓸 수 있습니다.궁금한 점이 더 있다면 말씀해 주세요:Comparable과 차이점이 필요하신가요?실제 코드 예제를 보고 싶으신가요?Stranger's LAB자바 [JAVA] - Comparable 과 Comparator의 이해 - Stranger's LAB2021. 4. 29. — 쉽게 말하자면, Comparable은 자기 자신과 파라미터로 들어오는 객체를 비교하는 것이고, Comparator는 자기 자신의 상태가 어떻던 상관없이 파라미터로...인프런Comparable, Comparator의 차이 - 인프런 | 커뮤니티 질문&답변2024. 9. 6. — - Comparable은 해당 객체의 기본 정렬 기준을 내부적으로 정의하고자 할 때 사용합니다. - Comparator는 기본 정렬 기준 외에 다른 기준으로 정렬...velogJava Comparator 이해하기 - velog2025. 1. 1. — Comparator는 객체를 비교해서 정렬 순서를 정해주는 Java 인터페이스다. 정렬 기준을 직접 정의할 수 있기 때문에 우리가 원하는 방식으로 데이터를 정렬 ...YouTube·Java Guides6:28Difference Between Comparable and Comparator in JavaYouTube·남궁성의 정석코딩29:21[Java Essentials - Basics] Chapter 11-30~33 Comparator and ComparableYouTube·Java Coding Community3mComparable Interface vs Comparator Interface | Java Tutorialwww.gyuray.dev[Java] Comparator, Comparable이란? - 디카페인 코딩어떤 객체에 내장된 정렬 기준을 바꿀 수 없는데 다른 기준을 사용하고 싶을 때 정렬 기준을 정의하는 방법이다. Comparator 인터페이스를 보면 메서드는 아래...    AI 답변에 오류가 있을 수 있습니다. 자세히 알아보기