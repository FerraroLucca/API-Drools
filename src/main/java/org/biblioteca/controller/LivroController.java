package org.biblioteca.controller;

import org.biblioteca.model.Livro;
import org.biblioteca.model.Pessoa;
import org.biblioteca.service.LivroService;
import org.biblioteca.service.RegraService;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService livroService;
    private final RegraService regraService;

    public LivroController(LivroService livroService, RegraService regraService) {
        this.livroService = livroService;
        this.regraService = regraService;
    }

    @PostMapping("/NovoLivro")
    public String save(@RequestBody Livro livro) {
        livroService.salvar(livro);
        return "Livro adicionado com sucesso!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Livro> livro = livroService.buscarPorId(id);
        if (livro.isEmpty()) {
            return "Livro não encontrado!";
        }
        livroService.excluir(id);
        return "Livro excluído com sucesso!";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody Livro novoLivro) {
        Optional<Livro> livroExistente = livroService.buscarPorId(id);
        if (livroExistente.isEmpty()) {
            return "Livro não encontrado!";
        }

        Livro livro = livroExistente.get();
        livro.setAutor(novoLivro.getAutor());
        livro.setPessoa(novoLivro.getPessoa());
        livro.setDataEmprestimo(novoLivro.getDataEmprestimo());

        livroService.salvar(livro);
        return "Livro atualizado com sucesso!";
    }

    @GetMapping("/")
    public List<Livro> listAll() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}/validar")
    public String validarLivro(@PathVariable Long id) {
        Optional<Livro> livro = livroService.buscarPorId(id);
        if (livro.isEmpty()) {
            return "Livro não encontrado!";
        }

        KieSession kieSession = regraService.buscarRegrasPorTipo("VALIDACAO_LIVRO");
        kieSession.insert(livro.get());
        kieSession.fireAllRules();
        kieSession.dispose();

        return "Regras aplicadas! Verifique logs para detalhes.";
    }

    @GetMapping("/{id}/calcular-valor")
    public String calcularValor(@PathVariable Long id) {
        Optional<Livro> livro = livroService.buscarPorId(id);
        if (livro.isEmpty()) {
            return "Livro não encontrado!";
        }

        KieSession kieSession = regraService.buscarRegrasPorTipo("CALCULO_VALOR");
        kieSession.insert(livro.get());
        kieSession.fireAllRules();
        kieSession.dispose();

        return "Cálculo do valor realizado! Verifique logs para detalhes.";
    }

}
