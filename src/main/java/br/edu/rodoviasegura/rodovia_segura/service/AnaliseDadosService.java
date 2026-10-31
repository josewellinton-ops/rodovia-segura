
package br.edu.rodoviasegura.rodovia_segura.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnaliseDadosService {

    public void analisarCategorias(String caminhoArquivo) {

        String[] categorias = {
                "causa_acidente",
                "tipo_acidente",
                "classificacao_acidente",
                "condicao_metereologica",
                "municipio"
        };

        Map<String, Map<String, Integer>> resultados = new HashMap<>();

        for (String categoria : categorias) {
            resultados.put(categoria, new HashMap<>());
        }

        try {

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            try (Reader arquivo = Files.newBufferedReader(
                    Path.of(caminhoArquivo),
                    StandardCharsets.ISO_8859_1);

                 CSVReader leitor = new CSVReaderBuilder(arquivo)
                         .withCSVParser(parser)
                         .build()) {

                String[] cabecalho = leitor.readNext();

                if (cabecalho == null) {
                    System.out.println("Arquivo CSV vazio.");
                    return;
                }

                Map<String, Integer> colunas = new HashMap<>();

                for (int i = 0; i < cabecalho.length; i++) {
                    colunas.put(cabecalho[i].trim(), i);
                }

                String[] registro;

                while ((registro = leitor.readNext()) != null) {

                    for (String categoria : categorias) {

                        String valor = obterValor(
                                registro,
                                colunas,
                                categoria
                        );

                        if (!valor.isBlank()) {

                            Map<String, Integer> valores =
                                    resultados.get(categoria);

                            valores.put(
                                    valor,
                                    valores.getOrDefault(valor, 0) + 1
                            );
                        }
                    }
                }
            }

            System.out.println("\n========== ANÁLISE DAS CATEGORIAS ==========");

            for (String categoria : categorias) {

                Map<String, Integer> valores =
                        resultados.get(categoria);

                System.out.println("\nCATEGORIA: " + categoria);
                System.out.println("Quantidade de valores diferentes: "
                        + valores.size());

                valores.entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue()
                                .reversed())
                        .limit(20)
                        .forEach(item ->
                                System.out.println(
                                        item.getKey() + " = "
                                                + item.getValue()
                                                + " ocorrência(s)"
                                )
                        );
            }

            System.out.println("\n============================================");

        } catch (Exception e) {

            System.out.println("Erro ao analisar categorias: "
                    + e.getMessage());

            e.printStackTrace();
        }
    }

    private String obterValor(
            String[] registro,
            Map<String, Integer> colunas,
            String nomeColuna) {

        Integer indice = colunas.get(nomeColuna);

        if (indice == null || indice >= registro.length) {
            return "";
        }

        if (registro[indice] == null) {
            return "";
        }

        return registro[indice].trim();
    }
}
