import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame("groomis Jbutton");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JButton 생성
        JButton button = new JButton("click now");
        

        // 버튼에 ActionListener 등록
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("button button");
            }
        });

        // 버튼을 프레임에 추가
        

        // 프레임을 표시
        
    }
}
