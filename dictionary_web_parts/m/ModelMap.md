`org.springframework.ui.ModelMap`은 Spring Boot에서 사용하는 Spring MVC의 클래스이며, Controller에서 처리한 데이터를 View(화면)로 전달하기 위해 사용하는 객체입니다.

주로 JSP, Thymeleaf 같은 템플릿 엔진으로 데이터를 전달할 때 사용합니다. 전자정부 표준프레임워크 기반 프로젝트에서도 목록 조회 결과, 검색 조건, 페이징 정보 등을 화면에 전달할 때 자주 사용합니다.

## 1. `ModelMap`의 기본 의미

java
```
import org.springframework.ui.ModelMap;
```

`ModelMap`은 데이터를 이름(Key)과 값(Value)의 형태로 저장하는 객체입니다.

예를 들어 다음과 같이 데이터를 저장할 수 있습니다.

java
```
ModelMap model = new ModelMap();

model.addAttribute("userName", "홍길동");
model.addAttribute("pageIndex", 1);
model.addAttribute("totalCount", 100);
```

이 코드를 실행하면 `model`에 다음과 같은 데이터가 저장됩니다.

| 속성 이름(Key) | 값(Value) |
| --- | --- |
| `userName` | `"홍길동"` |
| `pageIndex` | `1` |
| `totalCount` | `100` |

여기서 `addAttribute()`는 화면에서 사용할 데이터를 Model에 추가하는 메서드입니다.

## 2. Controller에서 사용하는 예시

예를 들어 가속도계 정보 목록을 조회하고 화면에 전달한다고 가정하겠습니다.

java
```
@Controller
public class AcceleratorController {

    @GetMapping("/accelerators")
    public String selectAcceleratorList(ModelMap model) {

        List<String> acceleratorList =
                Arrays.asList("ACC001", "ACC002", "ACC003");

        model.addAttribute("acceleratorList", acceleratorList);
        model.addAttribute("totalCount", acceleratorList.size());

        return "accelerator/list";
    }
}
```

이 코드의 처리 흐름은 다음과 같습니다.

1. Controller 실행

가속도계 목록 조회

2. ModelMap에 데이터 저장

`acceleratorList`

`totalCount`

3. View로 전달

`accelerator/list` 화면에서 데이터 사용

여기서 다음 코드는

java
```
return "accelerator/list";
```

일반적인 Spring MVC의 View 처리 방식에서 `accelerator/list`라는 뷰 이름을 반환합니다. 실제 JSP나 템플릿 파일의 위치는 프로젝트의 View 설정에 따라 결정됩니다.

## 3. 화면에서는 어떻게 사용하나요?

JSP를 사용한다면 다음과 같이 ModelMap에 저장된 데이터를 조회할 수 있습니다.

jsp
```
<p>전체 개수: ${totalCount}</p>

<c:forEach var="item" items="${acceleratorList}">
    <p>${item}</p>
</c:forEach>
```

화면에는 다음과 같이 표시됩니다.

text
```
전체 개수: 3

ACC001
ACC002
ACC003
```

즉, Controller에서 `model.addAttribute()`로 저장한 데이터가 View에서 사용할 수 있는 상태가 됩니다.

## 4. `ModelMap`, `Model`, `ModelAndView`의 차이

Spring MVC에서는 화면에 데이터를 전달하기 위해 여러 방식을 사용할 수 있습니다.

| 구분 | 역할 |
| --- | --- |
| `Model` | 화면에 전달할 데이터를 저장하는 인터페이스 |
| `ModelMap` | 이름과 값 형태로 데이터를 저장하는 클래스 |
| `ModelAndView` | View 이름과 전달할 데이터를 함께 관리하는 클래스 |

예를 들어 `Model`과 `ModelMap`은 다음과 같이 사용할 수 있습니다.

java
```
@GetMapping("/example")
public String example(Model model) {
    model.addAttribute("message", "안녕하세요");
    return "example";
}
```

java
```
@GetMapping("/example")
public String example(ModelMap model) {
    model.addAttribute("message", "안녕하세요");
    return "example";
}
```

두 방식 모두 일반적인 Spring MVC 환경에서 화면에 `message` 데이터를 전달할 수 있습니다.

## 5. 전자정부 표준프레임워크에서 자주 보이는 사용 패턴

다음과 같은 코드를 발견할 수 있습니다.

java
```
public String selectAcceleratorList(
        @ModelAttribute("searchVO") SearchVO searchVO,
        ModelMap model) {

    List<AcceleratorVO> resultList =
            acceleratorService.selectAcceleratorList(searchVO);

    model.addAttribute("resultList", resultList);
    model.addAttribute("searchVO", searchVO);

    return "accelerator/list";
}
```

이 코드에서는 각 요소가 서로 다른 역할을 합니다.

- `@ModelAttribute("searchVO") SearchVO searchVO`: 검색 조건을 요청 파라미터에서 객체로 바인딩합니다.
- `acceleratorService.selectAcceleratorList(searchVO)`: 검색 조건을 이용해 목록을 조회합니다.
- `model.addAttribute("resultList", resultList)`: 조회 결과를 화면으로 전달합니다.
- `model.addAttribute("searchVO", searchVO)`: 검색 조건도 화면으로 전달해 검색어 유지 등에 활용합니다.
- `return "accelerator/list"`: 화면에 해당하는 View 이름을 반환합니다.

참고: `ModelMap`은 일반적으로 JSP나 Thymeleaf 같은 서버 렌더링 화면에 데이터를 전달할 때 사용합니다. REST API에서 `@RestController`로 JSON 응답을 반환하는 경우에는 보통 응답 DTO나 `ResponseEntity` 등을 사용하며, 화면 전달용 `ModelMap`은 사용하지 않습니다.

정리하면, `ModelMap`은 Controller에서 조회하거나 가공한 데이터를 이름을 붙여 저장하고, 화면에서 사용할 수 있도록 전달하는 역할을 합니다.
