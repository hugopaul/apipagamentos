package br.com.solidtechsolutions.apipagamentos.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/pending")
@RestController
@Slf4j
@CrossOrigin(origins = "${cors.config}")
public class PendingController {

    @GetMapping
    public RedirectView pagamento(){
        String redirectUrl = "https://shop.karoleeduardo.com.br/";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }
}
