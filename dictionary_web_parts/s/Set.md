Set 객체는 값의 컬렉션입니다. Set의 값은 한 번만 나타날 수 있으며, Set의 컬렉션에서는 고유한 값입니다. Set의 요소를 삽입 순서대로 순회할 수 있습니다. 삽입 순서는 각 요소가 add() 메서드에 의해 Set에 성공적으로 삽입된 순서(즉, add()가 호출될 때 이미 Set에 동일한 요소가 없는 경우)에 해당합니다.<br>
Set 객체는 수학 연산과 같이 집합을 구성할 수 있는 몇 가지 메서드를 제공합니다.<br>
이러한 메서드는 다음과 같습니다.<br>
<br>
A.difference(B) -> 반환 타입 -> Set<br>
A∖B 느낌<br>
A와 B의 차집합은 A에서 B와 겹치지 않는 부분입니다.<br>
<br>
A.intersection(B) -> 반환 타입 -> Set<br>
A∩B<br>
A와 B의 교집합은 두 원이 겹치는 부분입니다.<br>
<br>
A.symmetricDifference(B) -> 반환 타입 -> Set<br>
(A∖B)∪(B∖A)<br>
A와 B의 대칭차 집합은 두 원 중 어느 한 원이 포함하지만 둘 다 포함하지 않는 영역입니다.<br>
<br>
A.union(B) -> 반환 타입 -> Set<br>
A∪B<br>
A와 B의 대칭 차이는 두 원 중 하나 또는 두 원이 포함하는 영역입니다.<br>
<br>
A.isDisjointFrom(B) -> 반환 타입 -> Boolean<br>	
A∩B = ∅<br>	
 원이 겹치는 영역이 없기 때문에 A와 B는 분리소 집합입니다.<br>	
<br>	
