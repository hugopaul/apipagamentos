package br.com.solidtechsolutions.apipagamentos.controllers;

import br.com.solidtechsolutions.apipagamentos.models.Produto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/failure")
@RestController
@Slf4j
@CrossOrigin(origins = "https://kamylaelourival.com.br")
public class FaliureController {

    @GetMapping
    public RedirectView pagamento(){
        String redirectUrl = "https://kamylaelourival.com.br/weddingshop/";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }
}
