package br.com.solidtechsolutions.apipagamentos.controllers;

import br.com.solidtechsolutions.apipagamentos.services.PaymentService;
import br.com.solidtechsolutions.apipagamentos.services.impls.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/success")
@CrossOrigin(origins = "${cors.config}")
@Slf4j
public class SuccessController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    // Atualiza o produto como completamente pago
    @GetMapping("/{id}")
    public RedirectView productFullyPayment(@PathVariable Long id) {
        log.info("####### PAGAMENTO TOTAL RECEBIDO NO ID {} ############" ,id);
        // Chama o serviço para marcar o produto como completamente pago
        productService.updateProductAsFullyPaid(id);

        // Redireciona para a página de sucesso
        String redirectUrl = "https://kamylaelourival.com.br/weddingshop/";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }

    // Atualiza o produto com um pagamento parcial
    @GetMapping("/{id}/quotas/{payedQuotes}")
    public RedirectView partialyProductPayment(@PathVariable Long id, @PathVariable String payedQuotes) {
        log.info("####### PAGAMENTO PARCIAL RECEBIDO NO ID {}, COM {} COTAS ############" ,id, payedQuotes);

        // Chama o serviço para atualizar o pagamento parcial
        productService.updateProductWithPartialPayment(id, payedQuotes);

        // Redireciona para a página de sucesso
        String redirectUrl = "https://kamylaelourival.com.br/weddingshop/";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }
}
