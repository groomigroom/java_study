`java.util.Collections`는 스프링 부트 전용 클래스가 아니라, Java에서 컬렉션(List, Set, Map 등)을 다루는 데 필요한 다양한 유틸리티 메서드를 제공하는 표준 라이브러리 클래스입니다.

즉, Java 자체에 포함되어 있으며 스프링 부트와 관계없이 사용할 수 있습니다.

## 1. `Collections`는 어떤 역할을 하나요?

예를 들어 앞서 살펴본 전자정부 프레임워크의 코드에서 다음과 같이 사용했습니다.

java
```
@Override
public Set<String> getAllExpressionObjectNames() {
    return Collections.singleton("pagination");
}
```

여기서 `Collections.singleton("pagination")`은 문자열 `"pagination"` 하나만 포함하는 `Set`을 만들어 반환합니다.

결과를 개념적으로 표현하면 다음과 같습니다.

반환되는 Set

pagination

요소가 하나인 Set이며, 중복 요소를 허용하지 않습니다.

이 메서드는 Thymeleaf에 `"pagination"`이라는 이름의 표현식 객체를 제공한다는 사실을 알려주는 데 활용할 수 있습니다.

## 2. 자주 사용하는 `Collections` 메서드

| 메서드 | 역할 | 예시 |
| --- | --- | --- |
| `singleton()` | 요소 하나를 가진 Set 생성 | `Collections.singleton("A")` |
| `emptyList()` | 비어 있는 List 반환 | `Collections.emptyList()` |
| `emptySet()` | 비어 있는 Set 반환 | `Collections.emptySet()` |
| `emptyMap()` | 비어 있는 Map 반환 | `Collections.emptyMap()` |
| `sort()` | 리스트 정렬 | `Collections.sort(list)` |
| `reverse()` | 리스트 순서 뒤집기 | `Collections.reverse(list)` |
| `unmodifiableList()` | 수정할 수 없는 리스트 뷰 반환 | `Collections.unmodifiableList(list)` |

## 3. `Collections.singleton()`과 `Set.of()`는 어떻게 다른가요?

둘 다 요소 하나를 가진 Set을 만들 수 있습니다.

java
```
Set<String> names1 = Collections.singleton("pagination");

Set<String> names2 = Set.of("pagination");
```

두 코드 모두 `"pagination"` 하나를 포함하는 Set을 반환합니다.

- `Collections.singleton()`: Java 1.3부터 제공
- `Set.of()`: Java 9부터 제공

둘 다 반환된 Set에 요소를 추가하거나 삭제할 수 없습니다.

## 4. `Collection`과 `Collections`는 다른가요?

이름이 비슷해서 혼동하기 쉽습니다.

| 이름 | 의미 |
| --- | --- |
| `Collection` | 여러 요소를 담는 자료구조의 기본 인터페이스 |
| `Collections` | 컬렉션을 편리하게 다루도록 돕는 유틸리티 클래스 |
| `Collections.singleton()` | 요소 하나를 가진 Set을 생성하는 메서드 |

예를 들어 다음 코드를 보겠습니다.

java
```
Collection<String> names =
        Collections.singleton("pagination");
```

- 왼쪽의 `Collection<String>`은 변수의 타입입니다.
- 오른쪽의 `Collections.singleton(...)`은 Set을 생성하는 메서드 호출입니다.

정리하면, `java.util.Collections`는 스프링 부트 기능이 아니라 Java 표준 라이브러리의 컬렉션 유틸리티 클래스이며, 앞서 본 코드에서는 이름 하나를 담은 `Set`을 생성하기 위해 사용된 것입니다.

# reverseOrder()

```
자바에서 Collections.reverseOrder()는 Comparable 인터페이스를 구현한 객체들의 자연 정렬(오름차순)
기준을 뒤집어 내림차순 정렬에 사용하는 Comparator를 반환하는 정적 메서드입니다. 

핵심 의미

• 역순 비교자(Comparator) 생성: 요소 자체의 순서를 직접 뒤집는 것이 아니라, 정렬 메서드(Arrays.sort(),
Collections.sort(), PriorityQueue 등)에 넘겨 내림차순 기준으로 비교하게 만듭니다. 
• 기존 비교자 반전 오버로딩: Collections.reverseOrder(Comparator<T> cmp)처럼 특정 Comparator를
인자로 넣으면, 해당 비교자의 정렬 순서를 역순으로 뒤집은 Comparator를 반환합니다.
• 객체 타입 전용: String, Integer, Double 같은 참조(객체) 타입에만 사용할 수 있으며, int[] 같은 기본 타입 배열에는 직접 쓸 수 없습니다. 

사용 예시

• 객체 배열 내림차순 정렬: Arrays.sort(arr, Collections.reverseOrder());
• 리스트 내림차순 정렬: Collections.sort(list, Collections.reverseOrder());
• 최대 힙(PriorityQueue) 생성: new PriorityQueue<>(Collections.reverseOrder()); 

비슷한 기능과의 차이

• Collections.reverse(): 정렬 기준을 만드는 것이 아니라, 이미 있는 List의 요소 순서를 그대로 뒤집습니다.
• Comparator.reverseOrder(): Java 8 이상에서 제공하는 동일한 목적의 메서드입니다. 
자세한 예시는 자바 역순 정렬, Collections.reverseOrder() 사용이나 오름차순과 내림차순
(sort, reverseOrder, reverse)에서 확인할 수 있습니다.
배열이나 리스트, 우선순위 큐(PriorityQueue), 또는 사용자 정의 객체 정렬 중
어떤 상황에 적용할지 알려주시면 맞춤형 코드 예시를 보여드릴 수 있습니다.

```
