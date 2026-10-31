
package br.edu.rodoviasegura.rodovia_segura.repository;

import br.edu.rodoviasegura.rodovia_segura.model.Localidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {

    Optional<Localidade> findByMunicipioAndUf(String municipio, String uf);

}
