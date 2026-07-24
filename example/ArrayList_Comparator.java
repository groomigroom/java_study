import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
      ArrayList<String> list = new ArrayList<>();

      list.add("groomi");
      list.add("groomii");
      list.add("groomiiiii");

      list.sort(new Comparator<String>(){
        @Override
        public int compare(String str1, String str2) {
          return str1.compareTo(str2);
        }
      });

      list.sort((Comparator<String>) (str1, str2) -> str1.compareTo(str2));
    }
}
