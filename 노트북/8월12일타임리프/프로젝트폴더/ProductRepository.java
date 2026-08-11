package com.example.demo.repository;
import com.example.demo.domain.Product;import java.util.*;
public class ProductRepository {
    private static final ProductRepository instance = new ProductRepository();
    private final Map<Integer, Product> store = new HashMap<>();

    private ProductRepository() {
        // 테스트용 샘플 데이터 세팅
        store.put(1, new Product(1, "노트북", 1500000));
        store.put(2, new Product(2, "스마트폰", 1000000));
        store.put(3, new Product(3, "무선 이어폰", 200000));
    }

    public static ProductRepository getInstance() {
        return instance;
    }

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    public Product findById(Integer id) {
        return store.get(id);
    }
}
