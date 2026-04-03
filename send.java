import java.util.Scanner;
public {
	void main {
		Scanner s = new Scanner(System.in);

		String personName, psersonAddr;
		int weight;

		System.out.println("## 택배 보내기입니다. 다음을 각각 입력하세요 ##");

		System.out.print("받는 사람 : ");
		personName = s.nextLine();
		System.out.print("주소 : ");
		psersonAddr = s.nextLine();
		System.out.print("무게(g) : ");
		weight = s.nextInt();
		System.out.println("** 받는 사람 ==>" + personName);
		System.out.println("** 주소 ==>" + psersonAddr);
		System.out.println("** 배송비 ==>" + weight*5 + "원");

		s.close();

	}
}

/*
Java에서 s.nextLine()은 java.util.Scanner 객체(s)를 사용하여 사용자가 입력한 엔터키(개행 문자, \n)를 만날 때까지 한 줄 전체를 문자열(String)로 읽어오는 메서드입니다. 공백을 포함하여 한 줄 전체를 가져오며, 엔터키 이후의 내용은 가져오지 않습니다. 
주요 특징 및 의미:
한 줄 전체 읽기: 공백(Space)이 있어도 줄바꿈이 일어나기 전까지 모두 하나의 문자열로 반환합니다.
개행 문자 처리: 입력된 데이터뿐만 아니라 마지막의 개행 문자(\n)까지 소비(소모)하여, 다음 입력 작업이 정상적으로 작동하도록 합니다.
주의점 (nextInt와 혼용 시): nextInt(), nextDouble() 등 숫자 입력 후 nextLine()을 사용하면, 숫자 뒤의 엔터키(\n)를 nextLine()이 읽어 빈 문자열을 반환하는 문제가 발생할 수 있어 주의가 필요합니다. 
*/
