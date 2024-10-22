package br.com.solidtechsolutions.apipagamentos.controllers;


import br.com.solidtechsolutions.apipagamentos.models.Produto;
import br.com.solidtechsolutions.apipagamentos.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/payments")
@RestController
@Slf4j
@CrossOrigin(origins = "${cors.config}")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> pagamento(@RequestBody Produto produto){
        log.info("pagamento recebido" + produto.toString());
        return paymentService.executePayment(produto);
    }

}
