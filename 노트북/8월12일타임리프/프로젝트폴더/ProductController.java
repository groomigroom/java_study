package com.example.demo.controller;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    // ❌ private final ProductService productService = new ProductService(); (삭제)
    
    // ⭕ 스프링 빈으로 등록된 서비스를 생성자를 통해 주입받도록 변경 (DI)
    private final ProductService productService;

    // 스프링 부트가 자동으로 서비스 빈을 찾아서 넣어줍니다 (생성자가 1개면 @Autowired 생략 가능)
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 전체 목록 조회
    @GetMapping
    public String list(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/list"; // src/main/resources/templates/product/list.html 파일 호출
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/detail"; // src/main/resources/templates/product/detail.html 파일 호출
    }
}
