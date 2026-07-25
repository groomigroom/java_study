# java_study

example에 AraayList 봐보기...
ArrayList_Comparator.java 도 보기...

아래꺼부터 보기....

https://github.com/lleellee0/java-for-backend/blob/main/03/3-3-11.java

자바에서 Optional의 의미    +4                    자바의 Optional은 null이 올 수 있는 값을 감싸는 래퍼 클래스(Wrapper Class)로, 값이 존재하지 않을 수 있음(null)을 명시하고 NullPointerException(NPE)을 안전하게 방지하기 위해 자바 8부터 도입된 기능입니다. 주요 특징으로는 Optional.ofNullable()을 통한 생성, orElse()를 통한 대체 값 지정, 그리고 값의 존재 여부를 확인하는 다양한 메서드 제공 등이 있습니다.주요 특징 및 목적Null 안전성: null을 직접 다루지 않고 컨테이너로 감싸서 처리하므로 예기치 않은 NPE 위험을 줄여줍니다.의도 표현: 메서드의 반환 타입으로 사용하여 "이 메서드는 결과가 없을 수도 있다"는 개발자의 의도를 명확히 전달합니다.다양한 메서드: 값이 없을 때 기본값을 반환하는 orElseGet(), 조건에 따라 실행하는 ifPresent() 등 가독성 좋은 코드를 작성하게 돕습니다.주의 사항반환 타입으로 제한: 메서드의 파라미터나 클래스의 필드로 사용하는 것은 권장되지 않으며, 주로 반환 값으로 사용하도록 설계되었습니다.성능 고려: 객체를 새로 생성하므로 무분별하게 사용하면 메모리 성능에 안 좋은 영향을 줄 수 있습니다.궁금한 점이 있다면 말씀해 주세요:Optional을 생성하고 사용하는 구체적인 코드 예시가 필요하신가요?아니면 잘못 사용하고 있는 안티패턴(Anti-Pattern)에 대해 더 알고 싶으신가요?티스토리[Java] Optional이란? Optional 개념 및 사용법 - (1/2) - MangKyu's Diary2020. 4. 7. — 자바 Optional은 null이 올 수 있는 값을 감싸는 Wrapper 클래스로, null 검사를 하지 않아도 NPE(NullPointerException)를 ...티스토리[JAVA] Optional의 개념과 사용법, 주의할 점2024. 5. 1. — optional이란? · 존재할 수도 있고, 존재하지 않을 수도 있는 객체, 즉 null이 될 수도 있는 객체를 감싸주는 Wrapper 클래스 · 따라서 Opti...YouTube·우아한테크2m[10 Minute Tech Talk] Joyseon's Java Optionalvelog[JAVA Optional] Optional에 대해 알아보자 - velog2023. 5. 7. — Optional이란? JAVA의 영원한 숙적인 NullPointerException을 방지해주는. 즉, null인 값을 참조해도 NullPointerExcepti...YouTube·어라운드 허브 스튜디오 - Around Hub Studio1mOptional 이론편 [ 자바 (Java) 기초 ]incodom.krjava/java.util.Optional - 인코덤, 생물정보 전문위키2024. 6. 27. — Optional 단점 # 성능 오버헤드: Optional 객체를 생성하고 처리하는 데 추가적인 메모리와 성능 오버헤드가 발생할 수 있다. 과도한 사용: 모든 곳에...    AI 답변에 오류가 있을 수 있습니다. 자세히 알아보기




백엔드 맥북하기 ------

https://www.youtube.com/watch?v=7jhVJIYi8fc&list=PLVsNizTWUw7FBMFX9pezh5Gxg5AtNmoMv&index=17



아래꺼도 해보기 -----

https://www.youtube.com/watch?v=z5fRj7B7alk&list=PLVsNizTWUw7FBMFX9pezh5Gxg5AtNmoMv&index=18


아래꺼 실습용 코드가 https://github.com/lleellee0/java-for-backend/blob/main/07/7-3-1.html

https://www.youtube.com/watch?v=ibcvFFPV0MQ&list=WL&index=3

위에꺼 추가 영상 보기...

