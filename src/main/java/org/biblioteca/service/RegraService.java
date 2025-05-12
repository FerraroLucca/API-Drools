package org.biblioteca.service;

import org.biblioteca.repository.RegraRepository;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegraService {
    private final RegraRepository regraRepository;

    public RegraService(RegraRepository regraRepository) {
        this.regraRepository = regraRepository;
    }

    public KieSession buscarRegrasPorTipo(String tipo) {
        List<String> regras = regraRepository.buscarRegrasPorTipo(tipo);

        KieHelper kieHelper = new KieHelper();
        for (String regra : regras) {
            kieHelper.addContent(regra, ResourceType.DRL);
        }

        return kieHelper.build().newKieSession();
    }
}
