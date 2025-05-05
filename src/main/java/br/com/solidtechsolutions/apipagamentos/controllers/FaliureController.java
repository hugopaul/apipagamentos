package br.com.solidtechsolutions.apipagamentos.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/failure")
@RestController
@CrossOrigin(origins = "${cors.config}")
public class FaliureController {

    @GetMapping
    public RedirectView pagamento(){
        String redirectUrl = "https://shop.karoleeduardo.com.br/";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }
}
