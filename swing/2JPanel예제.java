import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // 프레임 생성
        JFrame frame = new JFrame("JPanel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // JPanel 생성
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // 레이아웃을 FlowLayout으로 설정

        // JLabel과 JButton 추가
        JLabel label = new JLabel("hello groomi");
        JButton button = new JButton("click with groomi");

        // JPanel에 컴포넌트들 추가
        panel.add(label);
        panel.add(button);

        // 프레임에 JPanel 추가
        frame.add(panel);

        // 프레임을 보이도록 설정
        frame.setVisible(true);
    }
}
