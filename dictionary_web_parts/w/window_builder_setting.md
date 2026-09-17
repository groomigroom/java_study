보내주신 화면을 보니 Java Build Path의 라이브러리 순서와 컴파일 경로가 꼬여 있는 상태입니다. 특히 JRE System Library 앞에 있는 체크박스가 해제되어 있고, src 폴더가 다른 경로로 등록되어 있어 자바 기본 라이브러리를 전혀 불러오지 못하고 있습니다.
이 창에서 딱 3가지만 변경해주시면 Unresolved compilation problems 에러가 완벽히 해결됩니다. 지금 열려 있는 속성 창에서 그대로 따라 해보세요.
------------------------------
## 해결 방법## 1. JRE System Library 체크박스 켜기

* 화면에 보이는 JRE System Library [JavaSE-17] 왼쪽에 있는 빈 체크박스를 클릭하여 체크(✓) 표시를 해줍니다.
* 이것이 해제되어 있으면 이클립스가 자바의 기본 기능(EventQueue, JFrame 등)을 프로젝트에 포함시키지 않습니다.

## 2. Libraries 탭에서 JRE 위치 변경하기 (가장 중요)
현재 자바 설정 구조가 모듈 방식으로 잘못 꼬여있을 확률이 높습니다. 이를 올바른 클래스패스로 옮겨주어야 합니다.

   1. 상단의 Libraries 탭을 누릅니다.
   2. 목록에 만약 Modulepath와 Classpath라는 두 개의 그룹이 보인다면, Modulepath 하위에 있는 JRE System Library를 선택합니다.
   3. 우측의 Remove 버튼을 눌러 지웁니다.
   4. 바로 아래에 있는 Classpath를 마우스로 클릭하여 선택합니다.
   5. 우측의 Add Library... ➡️ JRE System Library 선택 ➡️ Next를 누릅니다.
   6. Execution environment를 체크하고 JavaSE-17을 선택한 뒤 Finish를 누릅니다.
   7. 결과적으로 JRE System Library가 Modulepath가 아닌 Classpath 하위에 들어가 있어야 합니다.

## 3. 적용 후 프로젝트 클린(Clean)하기

   1. 창 우측 하단의 Apply and Close를 눌러 설정을 저장합니다.
   2. 설정을 바꾸고 나면 이클립스 상단 메뉴에서 Project > Clean...을 누릅니다.
   3. Clean 창이 뜨면 아래 Clean 버튼을 눌러 프로젝트를 완전히 새로고침 해줍니다.

------------------------------
설정을 마치고 나면 소스 코드에 떠 있던 빨간색 밑줄 에러들이 전부 사라질 것입니다.
만약 Libraries 탭으로 이동했는데 화면이 어떻게 구성되어 있는지 헷갈리신다면, Libraries 탭을 누른 상태의 화면을 다시 한번 캡처해서 보여주세요. 바로 이어서 안내해 드리겠습니다.
설정을 마치고 이클립스를 새로고침 했을 때, 코드 창의 빨간 줄 에러가 모두 사라졌는지 알려주세요. 에러가 사라졌다면 하단의 Design 탭이 정상적으로 열리는지 확인해 보실 수 있습니다.

