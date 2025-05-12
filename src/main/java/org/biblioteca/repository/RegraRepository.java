package org.biblioteca.repository;

import org.biblioteca.model.Regra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegraRepository extends JpaRepository<Regra, Long> {

    @Query("SELECT r.regra FROM Regra r WHERE r.tipo = :tipo")
    List<String> buscarRegrasPorTipo(String tipo);

}

