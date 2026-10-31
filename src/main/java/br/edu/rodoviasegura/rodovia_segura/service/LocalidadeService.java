
package br.edu.rodoviasegura.rodovia_segura.service;

import br.edu.rodoviasegura.rodovia_segura.model.Localidade;
import br.edu.rodoviasegura.rodovia_segura.repository.LocalidadeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalidadeService {

    private final LocalidadeRepository localidadeRepository;

    // Construtor: recebe o repositório
    public LocalidadeService(LocalidadeRepository localidadeRepository) {
        this.localidadeRepository = localidadeRepository;
    }


    @Transactional
    public Localidade buscarOuCriar(String municipio, String uf) {

        if (municipio == null || municipio.isBlank()) {
            throw new IllegalArgumentException("O município deve ser informado.");
        }

        if (uf == null || uf.isBlank()) {
            throw new IllegalArgumentException("A UF deve ser informada.");
        }

        // Criamos novas variáveis já padronizadas
        final String municipioFormatado =
                municipio.trim().toUpperCase();

        final String ufFormatada =
                uf.trim().toUpperCase();

        if (!ufFormatada.matches("[A-Z]{2}")) {
            throw new IllegalArgumentException("A UF deve possuir duas letras.");
        }

        return localidadeRepository
                .findByMunicipioAndUf(municipioFormatado, ufFormatada)

                .orElseGet(() -> {
                    Localidade novaLocalidade =
                            new Localidade(municipioFormatado, ufFormatada);

                    return localidadeRepository.save(novaLocalidade);
                });
    }

}

