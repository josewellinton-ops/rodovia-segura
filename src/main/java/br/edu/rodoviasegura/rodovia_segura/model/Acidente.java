
package br.edu.rodoviasegura.rodovia_segura.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "acidente")
public class Acidente {

    @Id
    private Long id;

    @Column(name = "data_inversa")
    private LocalDate data;

    @Column(name = "dia_semana", length = 20)
    private String diaSemana;

    private LocalTime horario;

    private Integer br;

    private BigDecimal km;

    @Column(name = "causa_acidente", length = 200)
    private String causaAcidente;

    @Column(name = "tipo_acidente", length = 150)
    private String tipoAcidente;

    @Column(name = "classificacao_acidente", length = 100)
    private String classificacaoAcidente;

    @Column(name = "fase_dia", length = 50)
    private String faseDia;

    @Column(name = "sentido_via", length = 30)
    private String sentidoVia;

    @Column(name = "condicao_metereologica", length = 100)
    private String condicaoMeteorologica;

    @Column(name = "tipo_pista", length = 50)
    private String tipoPista;

    @Column(name = "tracado_via", length = 100)
    private String tracadoVia;

    @Column(name = "uso_solo", length = 20)
    private String usoSolo;

    private Integer pessoas;
    private Integer mortos;

    @Column(name = "feridos_leves")
    private Integer feridosLeves;

    @Column(name = "feridos_graves")
    private Integer feridosGraves;

    private Integer ilesos;
    private Integer ignorados;
    private Integer feridos;
    private Integer veiculos;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private String regional;
    private String delegacia;
    private String uop;

    // Relacionamento com Localidade
    @ManyToOne
    @JoinColumn(name = "id_localidade", nullable = false)
    private Localidade localidade;

    // Construtor vazio exigido pelo JPA
    public Acidente() {
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Integer getBr() {
        return br;
    }

    public void setBr(Integer br) {
        this.br = br;
    }

    public BigDecimal getKm() {
        return km;
    }

    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public String getCausaAcidente() {
        return causaAcidente;
    }

    public void setCausaAcidente(String causaAcidente) {
        this.causaAcidente = causaAcidente;
    }

    public String getTipoAcidente() {
        return tipoAcidente;
    }

    public void setTipoAcidente(String tipoAcidente) {
        this.tipoAcidente = tipoAcidente;
    }

    public String getClassificacaoAcidente() {
        return classificacaoAcidente;
    }

    public void setClassificacaoAcidente(String classificacaoAcidente) {
        this.classificacaoAcidente = classificacaoAcidente;
    }

    public String getFaseDia() {
        return faseDia;
    }

    public void setFaseDia(String faseDia) {
        this.faseDia = faseDia;
    }

    public String getSentidoVia() {
        return sentidoVia;
    }

    public void setSentidoVia(String sentidoVia) {
        this.sentidoVia = sentidoVia;
    }

    public String getCondicaoMeteorologica() {
        return condicaoMeteorologica;
    }

    public void setCondicaoMeteorologica(String condicaoMeteorologica) {
        this.condicaoMeteorologica = condicaoMeteorologica;
    }

    public String getTipoPista() {
        return tipoPista;
    }

    public void setTipoPista(String tipoPista) {
        this.tipoPista = tipoPista;
    }

    public String getTracadoVia() {
        return tracadoVia;
    }

    public void setTracadoVia(String tracadoVia) {
        this.tracadoVia = tracadoVia;
    }

    public String getUsoSolo() {
        return usoSolo;
    }

    public void setUsoSolo(String usoSolo) {
        this.usoSolo = usoSolo;
    }

    public Integer getPessoas() {
        return pessoas;
    }

    public void setPessoas(Integer pessoas) {
        this.pessoas = pessoas;
    }

    public Integer getMortos() {
        return mortos;
    }

    public void setMortos(Integer mortos) {
        this.mortos = mortos;
    }

    public Integer getFeridosLeves() {
        return feridosLeves;
    }

    public void setFeridosLeves(Integer feridosLeves) {
        this.feridosLeves = feridosLeves;
    }

    public Integer getFeridosGraves() {
        return feridosGraves;
    }

    public void setFeridosGraves(Integer feridosGraves) {
        this.feridosGraves = feridosGraves;
    }

    public Integer getIlesos() {
        return ilesos;
    }

    public void setIlesos(Integer ilesos) {
        this.ilesos = ilesos;
    }

    public Integer getIgnorados() {
        return ignorados;
    }

    public void setIgnorados(Integer ignorados) {
        this.ignorados = ignorados;
    }

    public Integer getFeridos() {
        return feridos;
    }

    public void setFeridos(Integer feridos) {
        this.feridos = feridos;
    }

    public Integer getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(Integer veiculos) {
        this.veiculos = veiculos;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getDelegacia() {
        return delegacia;
    }

    public void setDelegacia(String delegacia) {
        this.delegacia = delegacia;
    }

    public String getUop() {
        return uop;
    }

    public void setUop(String uop) {
        this.uop = uop;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }
}
