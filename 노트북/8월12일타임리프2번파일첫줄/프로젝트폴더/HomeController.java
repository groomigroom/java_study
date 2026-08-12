package groomi.groomgroom; // 본인의 패키지 경로에 맞게 수정
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Calendar;

@Controller
public class HomeController {

    // ControllerMappings 클래스 대신 @GetMapping으로 URL을 매핑합니다.
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        
        // WebContext.setVariable 대신 스프링의 Model에 데이터를 담습니다.
        model.addAttribute("welcomeMessage", "안녕하세요! GTVG 쇼핑몰에 오신 것을 환영합니다.");
        model.addAttribute("today", Calendar.getInstance());

        // 리턴한 문자열을 바탕으로 뷰 리졸버가 templates/home.html을 찾습니다.
        return "home";
    }
}
