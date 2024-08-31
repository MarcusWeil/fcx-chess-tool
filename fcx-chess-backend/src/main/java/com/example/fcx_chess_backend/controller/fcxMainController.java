package com.example.meuprojetobackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class fcxMainController {

    @GetMapping("/api/dados")
    public String getDados() {
        return "Dados recebidos com sucesso!";
    }

    @PostMapping("/api/dados")
    public String postDados(@RequestBody String dados) {
        return "Dados enviados: " + dados;
    }
}
