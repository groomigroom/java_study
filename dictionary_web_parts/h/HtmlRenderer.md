import org.commonmark.renderer.html.HtmlRenderer;

자바 스프링 부트에서 import org.commonmark.renderer.html.HtmlRenderer;의 HtmlRenderer는 마크다운 구조(Node 트리)를 웹 브라우저가 읽을 수 있는 HTML 태그 문자열로 최종 변환(렌더링)해 주는 출력기 클래스입니다.
앞서 보았던 Parser가 마크다운 텍스트를 분석해 Node라는 자바 객체 조각들로 만들었다면, HtmlRenderer는 그 조각들을 조립해 진짜 <p>, <h1>, <strong> 같은 HTML 코드로 뽑아내는 마무리를 담당합니다.
------------------------------
## ⚙️ HtmlRenderer의 핵심 역할

   1. HTML 태그 자동 생성 및 변환: Node 트리 내부의 구성 요소들을 매핑되는 HTML 태그로 1:1 치환합니다.
   * Heading (Level 2) 노드 ➡️ <h2>제목</h2>
      * StrongEmphasis 노드 ➡️ <strong>굵은글씨</strong>
   2. XSS(크로스 사이트 스크립팅) 방지 및 안전한 출력: 사용자가 마크다운 내부에 악의적인 자바스크립트(<script>alert('공격')</script>)를 심었을 때, 이를 안전하게 이스케이프(Escape) 처리하거나 무력화하여 웹 취약점을 방지하는 옵션을 설정할 수 있습니다.

------------------------------
## 💻 코드 흐름으로 보는 HtmlRenderer의 위치
마크다운 변환은 언제나 [Parser] ➡️ [Node] ➡️ [HtmlRenderer]의 3단계 파이프라인으로 움직입니다.

import org.commonmark.node.Node;import org.commonmark.parser.Parser;import org.commonmark.renderer.html.HtmlRenderer; // ◀ 질문하신 클래스
public class MarkdownConverter {
    public static String convert(String markdownText) {
        
        Parser parser = Parser.builder().build();
        // 1단계: 마크다운 텍스트를 분석하여 Node 객체 트리로 변환
        Node document = parser.parse(markdownText); 
        
        // 2단계: HtmlRenderer 객체 생성
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        
        // 3단계: [HtmlRenderer의 역할] Node 트리를 순수한 HTML 문자열로 최종 출력
        return renderer.render(document); 
    }
}

예를 들어 사용자가 입력한 markdownText가 **성공**이라면, renderer.render(document)를 거친 최종 결과물은 <p><strong>성공</strong></p> 문자열이 되어 리턴됩니다.
------------------------------
## 💡 요약

* 의미: 자바 객체로 변환되어 있던 마크다운 데이터를 HTML 웹 문서 표준 규격으로 빌드해 주는 변환 가공기입니다.
* 역할: Node 트리 데이터를 입력받아 브라우저가 화면에 예쁘게 그릴 수 있는 최종 HTML 텍스트(String)를 리턴합니다.

이제 Parser, Node, HtmlRenderer까지 마크다운 변환에 필요한 핵심 3인방을 모두 파악하셨습니다! 이렇게 변환된 HTML 문자열을 화면에 출력할 때 Thymeleaf(타임리프) 템플릿에서 th:text 대신 th:utext를 써야 태그가 깨지지 않고 반영되는데, 혹시 타임리프 화면 연동 단계까지 구현이 완료되셨나요?
아직 화면 연동 코드가 고민이시라면 Controller와 HTML(Thymeleaf) 단의 연동 예시 코드를 제안해 드릴 수 있습니다. 어떻게 진행해 드릴까요?

