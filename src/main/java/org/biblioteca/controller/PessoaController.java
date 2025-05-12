package org.biblioteca.controller;

import org.biblioteca.model.Pessoa;
import org.biblioteca.service.PessoaService;
import org.biblioteca.service.RegraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    private final RegraService regraService;

    public PessoaController(PessoaService pessoaService, RegraService regraService) {
        this.pessoaService = pessoaService;
        this.regraService = regraService;
    }

    @GetMapping("/")
    public List<Pessoa> listAll() {
        return pessoaService.listarTodos();
    }

    @PostMapping("/NovaPessoa")
    public String save(@RequestBody Pessoa pessoa) {
        pessoaService.salvar(pessoa);
        return "Pessoa adicionada com sucesso!";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody Pessoa novaPessoa) {
        Optional<Pessoa> pessoaExistente = pessoaService.buscarPorId(id);
        if (pessoaExistente.isEmpty()) {
            return "Pessoa não encontrada!";
        }

        Pessoa pessoa = pessoaExistente.get();
        pessoa.setNome(novaPessoa.getNome());
        pessoa.setLivrosEmprestados(novaPessoa.getLivrosEmprestados());

        pessoaService.salvar(pessoa);
        return "Pessoa atualizada com sucesso!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaService.buscarPorId(id);
        if (pessoa.isEmpty()) {
            return "Pessoa não encontrada!";
        }
        pessoaService.deletar(id);
        return "Pessoa excluída com sucesso!";
    }
}
