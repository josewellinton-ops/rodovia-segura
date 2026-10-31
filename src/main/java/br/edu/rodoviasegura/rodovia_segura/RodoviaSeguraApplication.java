
package br.edu.rodoviasegura.rodovia_segura;

import br.edu.rodoviasegura.rodovia_segura.service.AnaliseDadosService;
import br.edu.rodoviasegura.rodovia_segura.service.ImportacaoAcidentesService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RodoviaSeguraApplication implements CommandLineRunner {

	private final ImportacaoAcidentesService importacaoService;
	private final AnaliseDadosService analiseDadosService;

	public RodoviaSeguraApplication(
			ImportacaoAcidentesService importacaoService,
			AnaliseDadosService analiseDadosService) {

		this.importacaoService = importacaoService;
		this.analiseDadosService = analiseDadosService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RodoviaSeguraApplication.class, args);
	}

	@Override
	public void run(String... args) {

		if (args.length > 0 && args[0].equals("testar-csv")) {

			System.out.println("TESTANDO LEITURA DO CSV...");
			importacaoService.testarLeitura("data/datatran2023.csv");

		} else if (args.length > 0 && args[0].equals("importar-primeiro")) {

			System.out.println("IMPORTANDO PRIMEIRO ACIDENTE...");
			importacaoService.importarPrimeiroRegistro("data/datatran2023.csv");

		} else if (args.length > 0 && args[0].equals("analisar-csv")) {

			System.out.println("INICIANDO ANÁLISE DOS DADOS...");
			analiseDadosService.analisarArquivo("data/datatran2023.csv");

		} else {

			System.out.println("Sistema Rodovia Segura iniciado.");
			System.out.println("Nenhuma operação solicitada.");

		}
	}
}
