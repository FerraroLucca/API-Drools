package org.biblioteca.controller;

import org.biblioteca.service.RegraService;
import org.kie.api.runtime.KieSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regras")
public class RegraController {
    private final RegraService regraService;

    public RegraController(RegraService regraService) {
        this.regraService = regraService;
    }

    @GetMapping("/executar/{tipo}")
    public ResponseEntity<String> executarRegras(@PathVariable String tipo) {
        KieSession kSession = regraService.buscarRegrasPorTipo(tipo);
        kSession.fireAllRules();
        System.out.println("RegraController carregada!");
        return ResponseEntity.ok("Regras executadas para tipo: " + tipo);
    }
}
