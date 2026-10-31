
package br.edu.rodoviasegura.rodovia_segura.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "localidade",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"municipio", "uf"}
                )
        }
)
public class Localidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String municipio;

    @Column(nullable = false, length = 2)
    private String uf;

    // Construtor vazio exigido pelo JPA
    public Localidade() {
    }

    // Construtor para facilitar a criação de localidades
    public Localidade(String municipio, String uf) {
        this.municipio = municipio;
        this.uf = uf;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
