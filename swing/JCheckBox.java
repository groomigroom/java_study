import javax.swing.JCheckBox;
import javax.swing.JFrame;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("kimgroomi");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //JCheckBox 생성
        JCheckBox checkBox = new JCheckBox("groomi check");
        
        frame.setVisible(true);
    }
}
