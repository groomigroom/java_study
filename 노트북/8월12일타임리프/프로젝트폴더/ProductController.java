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

    // Service를 스프링 빈으로 등록하여 사용하도록 설정 필요 (클래스 상단에 @Service 부착 필수)
    private final ProductService productService = new ProductService();

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
