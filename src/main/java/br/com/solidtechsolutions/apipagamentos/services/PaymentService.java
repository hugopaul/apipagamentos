package br.com.solidtechsolutions.apipagamentos.services;

import br.com.solidtechsolutions.apipagamentos.models.Produto;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<String> executePayment(Produto produto);
}
