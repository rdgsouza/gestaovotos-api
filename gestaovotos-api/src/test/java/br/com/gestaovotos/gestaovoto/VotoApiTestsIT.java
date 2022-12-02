package br.com.gestaovotos.gestaovoto;

import br.com.gestaovotos.domain.model.Associado;
import br.com.gestaovotos.domain.model.Pauta;
import br.com.gestaovotos.domain.model.Sessao;
import br.com.gestaovotos.domain.model.Voto;
import br.com.gestaovotos.domain.repository.VotoRepository;
import br.com.gestaovotos.domain.service.AssociadoService;
import br.com.gestaovotos.domain.service.PautaService;
import br.com.gestaovotos.domain.service.SessaoService;
import br.com.gestaovotos.util.DatabaseCleaner;
import br.com.gestaovotos.util.GeraCpf;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class VotoApiTestsIT {

    @LocalServerPort
    private int port;
    private static GeraCpf geraCpf = new GeraCpf();
    private static String cpfGerado = geraCpf.cpf();

    private static String cpfSemCaracteresEspeciais = geraCpf.removeCaracteresEspeciais(cpfGerado);
    private static Long pautaId = 1L;

    private static Long associadoId = 3L;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private AssociadoService associadoService;


    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private VotoRepository votoRepository;

    private static Pauta pauta;

    private Associado associado;

    private String requestBodyCreateVoto;


    private static String geraCpf() {
        GeraCpf geraCpf = new GeraCpf();
        String cpfGerado = geraCpf.cpf();

        return geraCpf.removeCaracteresEspeciais(cpfGerado);
    }

    @BeforeEach
    public void setUp() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus201_QuandoEnviadoUmVotoComSucesso() {

        RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyCreateVoto)
                .pathParams("pautaId", 1)
                .pathParams("sessaoId", 1)
                .accept(ContentType.JSON)
                .when()
                .post("/v1/pautas/{pautaId}/sessoes/{sessaoId}/votos")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus200_QuandoRetornarTodosOsVotosPorIdDaPauta() {

        RestAssured.given()
                .pathParams("pautaId", 1)
                .basePath("/v1/pautas/{pautaId}/votos")
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    private void prepararDados() {

        Associado associado1 = new Associado();
        associado1.setNome("Rodrigo Souza");
        associado1.setCpf(geraCpf());
        associado1.setEmail("rodrigo.s@gmail.com");

        Associado associado2 = new Associado();
        associado2.setNome("Nikola Souza");
        associado2.setCpf(geraCpf());
        associado2.setEmail("nikola.s@gmail.com");

        Associado associado3 = new Associado();
        associado3.setNome("Antonio Souza");
        associado3.setCpf(geraCpf());
        associado3.setEmail("antonio.s@gmail.com");

        associadoService.salvar(associado1);
        associadoService.salvar(associado2);
        associadoService.salvar(associado3);


        Pauta novaPauta = new Pauta();
        novaPauta.setDescricao("Testando a api de votação");
        novaPauta = pautaService.salvar(novaPauta);

        Sessao novaSessao = new Sessao();
        novaSessao.setDescricao("Testes na api de votação");
        novaSessao.setTempoAbertura(60L);
        novaSessao.setPauta(novaPauta);
        sessaoService.iniciarSessaoVotacao(novaSessao);

        Associado assosociado1 =
                associadoService.buscarOuFalhar(1L);

        pauta = pautaService.buscarOuFalhar(1L);

        Voto voto1 = new Voto();
        voto1.setOpcaoVoto(true);
        voto1.setCpf(associado1.getCpf());
        voto1.setAssociado(assosociado1);
        voto1.setPauta(pauta);

        votoRepository.save(voto1);

        Voto voto2 = new Voto();
        voto2.setOpcaoVoto(true);
        voto2.setCpf(associado2.getCpf());
        voto2.setAssociado(associado2);
        voto2.setPauta(pauta);

        votoRepository.save(voto2);

       associado = associadoService.buscarOuFalhar(3L);

       requestBodyCreateVoto =
                "{\n" +
                "    \"opcaoVoto\": false,\n" +
                "    \"cpf\":" +associado.getCpf()+ ", \n" +
                "     \"associado\": {\n" +
                "        \"id\": "+associadoId+"\n" +
                "    }\n" +
                "}";

    }
}
