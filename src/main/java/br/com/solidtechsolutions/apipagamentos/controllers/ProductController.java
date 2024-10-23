package br.com.solidtechsolutions.apipagamentos.controllers;

import br.com.solidtechsolutions.apipagamentos.models.Product;
import br.com.solidtechsolutions.apipagamentos.models.Review;
import br.com.solidtechsolutions.apipagamentos.services.impls.ProductService;
import br.com.solidtechsolutions.apipagamentos.services.impls.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "${cors.config}")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para salvar uma lista de produtos
    @PostMapping("/batch")
    public ResponseEntity<List<Product>> createProducts(@RequestBody List<Product> products) {
        List<Product> savedProducts = productService.saveProducts(products);
        return ResponseEntity.ok(savedProducts);
    }

    // Endpoint para retornar 4 produtos aleatórios
    @GetMapping("/random")
    public ResponseEntity<List<Product>> getRandomProducts() {
        List<Product> randomProducts = productService.getRandomProducts();
        return ResponseEntity.ok(randomProducts);
    }

    // Endpoint para adicionar um review a um produto existente
    @PostMapping("/{productId}/reviews")
    public void addReview(@PathVariable Long productId, @RequestBody Review newReview) {
        Product updatedProduct = reviewService.addReviewToProduct(productId, newReview);
    }
}
