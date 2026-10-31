
package br.edu.rodoviasegura.rodovia_segura.repository;

import br.edu.rodoviasegura.rodovia_segura.model.Acidente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcidenteRepository extends JpaRepository<Acidente, Long> {

    List<Acidente> findByLocalidadeMunicipio(String municipio);

    List<Acidente> findByLocalidadeUf(String uf);

    List<Acidente> findByClassificacaoAcidente(String classificacao);

}
