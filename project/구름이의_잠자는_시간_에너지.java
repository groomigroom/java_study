import java.util.*;

public class Main {
    static int sleepEnergy(int isleepTime) {
      return isleepTime * 100;
    }

    public static void main(String[] args) {
      int a = sleepEnergy(3);
      System.out.println(a);
    }
}
