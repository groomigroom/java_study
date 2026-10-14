인텔리제이(IntelliJ IDEA)에서 HTML 파일을 만들 때 해당 뷰포트(viewport) 태그가 자동으로 포함되게 하려면 HTML 파일 템플릿(File and Code Templates)을 수정하면 됩니다. [1] 
설정해 두면 앞으로 새 HTML 파일을 만들 때마다 매번 자동으로 뜨게 됩니다. 방법은 다음과 같습니다.
------------------------------
## ⚙️ 설정 방법 (영문 기준 / 한글 기준)

   1. 인텔리제이 설정을 엽니다.
   * Windows / Linux: Ctrl + Alt + S
      * macOS: Cmd + , (쉼표)
   2. 왼쪽 메뉴에서 Editor ➡️ File and Code Templates (에디터 ➡️ 파일 및 코드 템플릿)로 이동합니다.
   3. 중간의 Files 탭에서 HTML4 파일 또는 HTML5 파일을 선택합니다. (보통 HTML5를 수정하시면 됩니다.)
   4. 오른쪽 코드 창의 <head> 태그 내부에 원하는 meta 태그 코드를 붙여넣습니다.

[수정할 코드 예시]

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <!-- 이 부분에 추가해 줍니다 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <title>${TITLE}</title>
</head>
<body>
#[[$END$]]#
</body>
</html>


   1. 하단의 Apply 또는 OK를 눌러 저장합니다.

------------------------------
## 💡 꿀팁: 단축키로 빠르게 만들기 (Emmet 기능)
템플릿 설정을 바꾸지 않더라도, 빈 HTML 파일이나 편집창에서 ! (느낌표)를 입력한 뒤 Tab 키를 누르면 뷰포트 태그가 포함된 기본 HTML5 skeleton(틀)이 순식간에 자동으로 완성됩니다. [2] 
혹시 설정 화면을 찾기 어려우시거나, 기존에 이미 만들어진 파일들에 일괄적으로 적용하고 싶으신가요? 필요하시다면 단축키를 이용한 일괄 변경 방법도 함께 설명해 드릴게요!

[1] [https://seeminglyjs.tistory.com](https://seeminglyjs.tistory.com/584)
[2] [https://co1nam.tistory.com](https://co1nam.tistory.com/83)
