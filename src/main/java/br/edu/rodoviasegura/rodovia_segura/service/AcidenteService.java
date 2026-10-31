
package br.edu.rodoviasegura.rodovia_segura.service;

import br.edu.rodoviasegura.rodovia_segura.model.Acidente;
import br.edu.rodoviasegura.rodovia_segura.repository.AcidenteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcidenteService {

    private final AcidenteRepository acidenteRepository;

    // Construtor: recebe o repositório
    public AcidenteService(AcidenteRepository acidenteRepository) {
        this.acidenteRepository = acidenteRepository;
    }

    // Método responsável por cadastrar um acidente
    @Transactional
    public Acidente salvar(Acidente acidente) {

        // Verifica se o acidente foi informado
        if (acidente == null) {
            throw new IllegalArgumentException(
                    "Os dados do acidente devem ser informados."
            );
        }

        // Verifica se o acidente possui identificador
        if (acidente.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do acidente deve ser informado."
            );
        }

        // Verifica se existe uma localidade associada
        if (acidente.getLocalidade() == null) {
            throw new IllegalArgumentException(
                    "O acidente precisa estar associado a uma localidade."
            );
        }

        // Impede cadastrar o mesmo acidente duas vezes
        if (acidenteRepository.existsById(acidente.getId())) {
            throw new IllegalArgumentException(
                    "Esse acidente já está cadastrado."
            );
        }

        // Salva o acidente no banco de dados
        return acidenteRepository.save(acidente);
    }
}
