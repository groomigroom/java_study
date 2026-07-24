import java.util.*;

public class Main {
    static int sleepEnergy(int isleepTime) {
      return isleepTime * 100;
    }

    public static void main(String[] args) {
      //scanner로 강아지 이름 받기??
      String puppy_name = "김구름";
      //scanner로 강아지 수면 시간 받기??
      int a = sleepEnergy(3);
      System.out.println(puppy_name + a);

      int b = a / 100;

      for (int i = 0; i < b; i++) {
        a += 100;
      }

      System.out.println("비축할 에너지는 " + a + "입니다.");
    }
}
