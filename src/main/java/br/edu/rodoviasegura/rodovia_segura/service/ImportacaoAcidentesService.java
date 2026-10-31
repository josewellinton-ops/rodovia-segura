
package br.edu.rodoviasegura.rodovia_segura.service;

import br.edu.rodoviasegura.rodovia_segura.model.Acidente;
import br.edu.rodoviasegura.rodovia_segura.model.Localidade;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import org.springframework.stereotype.Service;

import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImportacaoAcidentesService {

    private final LocalidadeService localidadeService;
    private final AcidenteService acidenteService;

    public ImportacaoAcidentesService(
            LocalidadeService localidadeService,
            AcidenteService acidenteService) {

        this.localidadeService = localidadeService;
        this.acidenteService = acidenteService;
    }
    @Transactional
    public void importarPrimeiroRegistro(String caminhoArquivo) {

        try {

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            try (Reader arquivo = Files.newBufferedReader(
                    Path.of(caminhoArquivo),
                    StandardCharsets.ISO_8859_1
            );

                 CSVReader leitor = new CSVReaderBuilder(arquivo)
                         .withCSVParser(parser)
                         .build()) {

                String[] cabecalho = leitor.readNext();
                String[] registro = leitor.readNext();

                if (cabecalho == null || registro == null) {
                    System.out.println("Não existem registros para importar.");
                    return;
                }

                // Converte a linha do CSV em objeto Acidente.
                Acidente acidente = converterRegistro(cabecalho, registro);

                // Busca a localidade no banco ou cria uma nova.
                Localidade localidade = localidadeService.buscarOuCriar(
                        acidente.getLocalidade().getMunicipio(),
                        acidente.getLocalidade().getUf()
                );

                // Associa a localidade já salva ao acidente.
                acidente.setLocalidade(localidade);

                // Salva o acidente no banco.
                acidenteService.salvar(acidente);

                System.out.println("Primeiro acidente salvo com sucesso!");
                System.out.println("ID: " + acidente.getId());
                System.out.println("Município: " + localidade.getMunicipio());
                System.out.println("UF: " + localidade.getUf());

            }

        } catch (Exception e) {

            System.out.println("Erro ao importar acidente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void testarLeitura(String caminhoArquivo) {

        try {

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            try (Reader arquivo = Files.newBufferedReader(
                    Path.of(caminhoArquivo),
                    StandardCharsets.ISO_8859_1
            );

                 CSVReader leitor = new CSVReaderBuilder(arquivo)
                         .withCSVParser(parser)
                         .build()) {

                // Lê o cabeçalho do CSV.
                String[] cabecalho = leitor.readNext();

                if (cabecalho == null) {
                    System.out.println("O arquivo CSV está vazio.");
                    return;
                }

                // Lê o primeiro acidente.
                String[] registro = leitor.readNext();

                if (registro == null) {
                    System.out.println("Não existem registros no arquivo.");
                    return;
                }

                // Converte os dados para um objeto Java.
                Acidente acidente = converterRegistro(cabecalho, registro);

                System.out.println("\nACIDENTE CONVERTIDO COM SUCESSO!");

                System.out.println("ID: " + acidente.getId());
                System.out.println("Data: " + acidente.getData());
                System.out.println("Horário: " + acidente.getHorario());
                System.out.println("BR: " + acidente.getBr());
                System.out.println("KM: " + acidente.getKm());
                System.out.println("Município: "
                        + acidente.getLocalidade().getMunicipio());
                System.out.println("UF: "
                        + acidente.getLocalidade().getUf());
                System.out.println("Mortos: " + acidente.getMortos());
                System.out.println("Latitude: " + acidente.getLatitude());
                System.out.println("Longitude: " + acidente.getLongitude());

            }

        } catch (Exception e) {

            System.out.println("Erro na conversão: " + e.getMessage());
            e.printStackTrace();

        }
    }

    // Transforma uma linha do CSV em um objeto Acidente.
    private Acidente converterRegistro(
            String[] cabecalho,
            String[] registro) {

        // Relaciona o nome de cada coluna com sua posição.
        Map<String, Integer> colunas = new HashMap<>();

        for (int i = 0; i < cabecalho.length; i++) {
            colunas.put(cabecalho[i].trim(), i);
        }

        Acidente acidente = new Acidente();

        acidente.setId(Long.parseLong(valor(colunas, registro, "id")));

        acidente.setData(
                LocalDate.parse(valor(colunas, registro, "data_inversa"))
        );

        acidente.setDiaSemana(valor(colunas, registro, "dia_semana"));

        String horario = valor(colunas, registro, "horario");

        if (!horario.isBlank()) {
            acidente.setHorario(LocalTime.parse(horario));
        }

        acidente.setBr(converterInteiro(valor(colunas, registro, "br")));
        acidente.setKm(converterDecimal(valor(colunas, registro, "km")));

        acidente.setCausaAcidente(
                valor(colunas, registro, "causa_acidente")
        );

        acidente.setTipoAcidente(
                valor(colunas, registro, "tipo_acidente")
        );

        acidente.setClassificacaoAcidente(
                valor(colunas, registro, "classificacao_acidente")
        );

        acidente.setFaseDia(valor(colunas, registro, "fase_dia"));
        acidente.setSentidoVia(valor(colunas, registro, "sentido_via"));

        acidente.setCondicaoMeteorologica(
                valor(colunas, registro, "condicao_metereologica")
        );

        acidente.setTipoPista(valor(colunas, registro, "tipo_pista"));
        acidente.setTracadoVia(valor(colunas, registro, "tracado_via"));
        acidente.setUsoSolo(valor(colunas, registro, "uso_solo"));

        acidente.setPessoas(converterInteiro(
                valor(colunas, registro, "pessoas")));

        acidente.setMortos(converterInteiro(
                valor(colunas, registro, "mortos")));

        acidente.setFeridosLeves(converterInteiro(
                valor(colunas, registro, "feridos_leves")));

        acidente.setFeridosGraves(converterInteiro(
                valor(colunas, registro, "feridos_graves")));

        acidente.setIlesos(converterInteiro(
                valor(colunas, registro, "ilesos")));

        acidente.setIgnorados(converterInteiro(
                valor(colunas, registro, "ignorados")));

        acidente.setFeridos(converterInteiro(
                valor(colunas, registro, "feridos")));

        acidente.setVeiculos(converterInteiro(
                valor(colunas, registro, "veiculos")));

        acidente.setLatitude(converterDecimal(
                valor(colunas, registro, "latitude")));

        acidente.setLongitude(converterDecimal(
                valor(colunas, registro, "longitude")));

        acidente.setRegional(valor(colunas, registro, "regional"));
        acidente.setDelegacia(valor(colunas, registro, "delegacia"));
        acidente.setUop(valor(colunas, registro, "uop"));

        // Cria a localidade relacionada ao acidente.
        Localidade localidade = new Localidade(
                valor(colunas, registro, "municipio"),
                valor(colunas, registro, "uf")
        );

        acidente.setLocalidade(localidade);

        return acidente;
    }

    // Busca o valor de uma coluna pelo nome.
    private String valor(
            Map<String, Integer> colunas,
            String[] registro,
            String nomeColuna) {

        Integer posicao = colunas.get(nomeColuna);

        if (posicao == null || posicao >= registro.length
                || registro[posicao] == null) {
            return "";
        }

        return registro[posicao].trim();
    }

    // Converte texto em número inteiro.
    private Integer converterInteiro(String valor) {

        if (valor == null || valor.isBlank()) {
            return null;
        }

        return Integer.parseInt(valor);
    }

    // Converte números que podem usar vírgula decimal.
    private BigDecimal converterDecimal(String valor) {

        if (valor == null || valor.isBlank()) {
            return null;
        }

        return new BigDecimal(valor.replace(",", "."));
    }
}
