# 예시
## Sort 객체의 작성 일시(createDate)를 역순(Desc)으로 조회하려면 Sort.Order.desc("createDate")와 같이 작성한다.
desc는 내림차순을 의미하고, asc는 오름차순을 의미한다.

## 만약 작성 일시 외에 정렬 조건을 추가하고 싶다면 sort.add 메서드를 활용해 sorts 리스트에 추가하면 된다.

# Sort.by()

## import org.springframework.data.domain.Sort;

자바 환경에서 Sort.by()는 순수 자바(표준 라이브러리)가 아닌, 스프링 프레임워크(특히 Spring Data JPA)에서 데이터베이스 쿼리 결과의 정렬 기준을 정의할 때 사용하는 핵심 메서드입니다. [1, 2] 
데이터베이스의 ORDER BY 문을 자바 코드로 안전하고 깔끔하게 생성해 주는 역할을 합니다. [1] 
------------------------------
## 1. 기본 사용법 (단일 조건 정렬)
정렬하고 싶은 엔티티의 필드명(변수명)을 문자열로 전달합니다. 기본값은 오름차순(ASC)입니다. [2] 

// 'price' 필드를 기준으로 오름차순 정렬
Sort.by("price") 
// 'price' 필드를 기준으로 내림차순(DESC) 정렬
Sort.by("price").descending() 

## 2. 여러 조건으로 정렬하기
정렬 기준이 여러 개일 때도 체이닝(and())이나 인자 값을 추가하여 간결하게 표현할 수 있습니다. [3] 

// 1순위: 가격 오름차순, 2순위: 등록일 내림차순
Sort.by("price").ascending().and(Sort.by("createdDate").descending())
// 여러 필드를 모두 오름차순으로 정렬할 때
Sort.by("category", "price") 

## 3. 실제 Repository에서 활용 예시
Spring Data JPA의 쿼리 메서드나 Pageable 객체와 결합하여 데이터베이스 조회를 수행합니다. [1, 4] 

// 1. Repository 인터페이스 정의public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name, Sort sort);
}
// 2. 서비스 레이어에서 호출
Sort sortOrder = Sort.by("price").descending();
List<Product> products = productRepository.findByName("노트북", sortOrder);

이 코드는 실제 DB에서 SELECT ... WHERE name = '노트북' ORDER BY price DESC; 형태의 쿼리로 실행됩니다.
------------------------------
## 💡 왜 메서드 이름(OrderBy) 대신 Sort.by()를 쓸까요?
Repository에 findByNameOrderByPriceDesc처럼 메서드 이름을 길게 채워 넣을 수도 있습니다. 하지만 Sort.by()를 매개변수로 던지면 다음과 같은 장점이 있습니다. [1, 5, 6] 

* 
* 동적 정렬 가능: 사용자가 화면에서 '가격순', '최신순' 버튼을 누를 때마다 하나의 메서드로 정렬 조건만 바꿔서 대응할 수 있습니다.
* 가독성 유지: 정렬 조건이 3~4개로 늘어나도 메서드 이름이 무한정 길어지는 것을 방지합니다. [1] 
* 

현재 Spring Data JPA를 활용한 게시판 페이징 처리를 구현 중이신가요, 아니면 단순 조건별 정렬 기능을 구현 중이신가요? 상황을 말씀해 주시면 맞춤형 코드를 안내해 드릴게요!

[1] [https://bezzang2.tistory.com](https://bezzang2.tistory.com/146)
[2] [https://www.youtube.com](https://www.youtube.com/watch?v=3M33vN9VPoc&t=20)
[3] [https://www.youtube.com](https://www.youtube.com/watch?v=5hZB_O1s72I)
[4] [https://merry-xmas.tistory.com](https://merry-xmas.tistory.com/24)
[5] [https://velog.io](https://velog.io/@nosibi/Spring-Data-JPA)
[6] [https://jistol.github.io](https://jistol.github.io/spring/2017/02/11/jpa-sort/)
