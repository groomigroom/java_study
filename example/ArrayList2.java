import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
      ArrayList<String> list = new ArrayList<>();

      list.add("groomi");
      list.add("groom");
      list.add("groomgroom");

      for (int i = 0; i < list.size(); i++) {
        System.out.println(list.get(i));
      }

      list.remove(1);
      int iindex = list.indexOf("groomgroom");
      System.out.println("groomgroom의 index = " + iindex);
    }
}
