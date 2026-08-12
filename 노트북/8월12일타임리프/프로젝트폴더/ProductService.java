package com.example.demo.service;
import com.example.demo.domain.Product;import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // 👈 중요: 스프링 컨테이너에 서비스 빈으로 등록합니다.
public class ProductService {

    // 테스트용 임시 메모리 저장소 (DB 연동 전까지 데이터 유지)
    private static final Map<Integer, Product> repository = new HashMap<>();
    private static int sequence = 0;

    // 더미(테스트) 데이터 생성자 초기화
    public ProductService() {
        save(new Product("아이폰 16", 1300000, "애플의 최신 스마트폰"));
        save(new Product("갤럭시 S26", 1250000, "삼성의 최신 플래그십 스마트폰"));
        save(new Product("맥북 에어", 1500000, "가볍고 강력한 노트북"));
    }

    // 상품 저장 기능
    public Product save(Product product) {
        product.setId(++sequence);
        repository.put(product.getId(), product);
        return product;
    }

    // 1. 상품 전체 목록 조회 (컨트롤러의 list 메서드에서 호출)
    public List<Product> findAll() {
        return new ArrayList<>(repository.values());
    }

    // 2. 상품 상세 조회 (컨트롤러의 detail 메서드에서 호출)
    public Product findById(Integer id) {
        return repository.get(id);
    }
}
