import java.util.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
      Scanner s = new Scanner(System.in);
      int imember;
      int icount;
      int iprice;
      
      System.out.println("아이돌 그룹 멤버수를 입력해 주세요. (40명 미만으로 입력)");
      imember = s.nextInt();
      
      System.out.println("음반 판매 수량을 입력해 주세요.\n");
      icount = s.nextInt();
      
      System.out.println("음반 1개의 가격을 입력해 주세요.(만원 단위 예: 1)\n");
      iprice = s.nextInt();
      //출력
      System.out.println("멤버수: " + imember);
      System.out.println("음반 판매 수량: " + icount);
      System.out.println("음반 1개의 가격: " + iprice);
      
      //정산금 출력
      if (imember >= 40) {
        System.out.println("멤버수를 40명 미만으로 정확히 입력해 주세요.");
      }
      else if ((imember >= 20) || (iprice <= 3)){
        System.out.println("멤버 1명당 정산금은" + iprice * icount * 0.02 + "만원입니다.");
      }
      else {
        System.out.println("멤버 1명당 정산금은" + iprice * icount * 0.05 + "만원입니다.");
      }
    }
}

/*
import java.util.*;는 자바에서 java.util 패키지에 포함된 모든 클래스(Class)와 인터페이스(Interface)를 현재 소스 파일로 가져와 사용하겠다는 의미입니다. *는 와일드카드(wildcard)로, 해당 패키지 내의 모든 요소를 불러옵니다.

40명 미만 아이돌 그룹 멤버수, 음반 판매 개수, 음반 개당 가격 (만원 단위)로 입력 받아서, 
멤버수가 20명 이상일 때 40명 미만이거나, 음반 가격이 3만원 이하일 때는, 음반 판매 금액의 2%를 각각 정산 받고,
멤버수가 20명 미만일 때는  음반 판매 금액의 5%를 각각 정산 받고,
***멤버수를 40명 이상으로 입력 받았을 때는 멤버수를 정확히 입력하라고 경고를 보내는 프로그램을 만드세요
*/
