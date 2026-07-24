import java.util.*;

public class Main {
    //잠자는데 사용되는 에너지 측정 함수
    static int sleepEnergy(int isleepTime) {
        return isleepTime * 100;
    }

    public static void main(String[] args) {
        Scanner Dogname = new Scanner(System.in);
        
        System.out.println("강아지 이름을 입력해 주세요");

        String puppy_name = Dogname.next();

        System.out.println(puppy_name + "의 수면시간을 입력해 주세요");

        int puppy_sleep_time = Dogname.nextInt();
        System.out.println(puppy_name + a);

        int b = a / 100;

        for (int i = 0; i < b; i++) {
            a += 100;
        }

        System.out.println("비축할 에너지는 " + a + "입니다.");

        String SforRice = "";

        if (a > 500) {
            SforRice = "맛있는사료";
        }
        else if (a > 300) {
            SforRice = "밍밍한사료";
        }
        else {
            SforRice = "일반사료";
        }

        System.out.println(SforRice);

        int iRicePrice;

        if (SforRice == "맛있는사료") {
            iRicePrice = 39000;
        }
        else if (SforRice == "밍밍한사료") {
            iRicePrice = 27000;
        }
        else {
            iRicePrice = 10000;
        }

        System.out.println(iRicePrice);

        //일시불 등등도 해보기
    }    
}
