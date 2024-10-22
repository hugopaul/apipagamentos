package br.com.solidtechsolutions.apipagamentos.repositories;


import br.com.solidtechsolutions.apipagamentos.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Consulta para obter 4 produtos aleatórios
    @Query(value = "SELECT * FROM product ORDER BY random() LIMIT 4", nativeQuery = true)
    List<Product> findRandomProducts();
}
