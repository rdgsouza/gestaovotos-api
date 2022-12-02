package br.com.gestaovotos.gestaovoto;

import br.com.gestaovotos.domain.model.Associado;
import br.com.gestaovotos.domain.model.Pauta;
import br.com.gestaovotos.domain.model.Sessao;
import br.com.gestaovotos.domain.model.Voto;
import br.com.gestaovotos.domain.repository.VotoRepository;
import br.com.gestaovotos.domain.service.AssociadoService;
import br.com.gestaovotos.domain.service.PautaService;
import br.com.gestaovotos.domain.service.SessaoService;
import br.com.gestaovotos.domain.service.VotoService;
import br.com.gestaovotos.exception.CpfDiferenteCadastroException;
import br.com.gestaovotos.util.DatabaseCleaner;
import br.com.gestaovotos.util.GeraCpf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class VotoIntegrantionTestsIT {

  @Autowired
  private PautaService pautaService;

  @Autowired
  private SessaoService sessaoService;

  @Autowired
  private VotoService votoService;

  @Autowired
  private AssociadoService associadoService;

  @Autowired
  private DatabaseCleaner databaseCleaner;


  @Autowired
  private VotoRepository votoRepository;


  private static Pauta pauta;

  private static Sessao sessao;


    @BeforeEach
    public void setUp() {
        databaseCleaner.clearTables();
        prepararDados();
    }

  @Test
  public void deveEnviarVotoComSucesso() {

      Associado assosociado =
              associadoService.buscarOuFalhar(3L);

        Voto novoVoto = new Voto();
        novoVoto.setPauta(pauta);
        novoVoto.setOpcaoVoto(true);

        novoVoto.setCpf(assosociado.getCpf());
        novoVoto.setAssociado(assosociado);

      novoVoto = votoService.enviarVoto(pauta.getId(), sessao.getId(), novoVoto);

        assertThat(novoVoto).isNotNull();
        assertThat(novoVoto.getId()).isNotNull();
  }


    @Test
  public void deveFalharAoEnviarUmVotoComCpfDireferenteDoCadastrado() {

        CpfDiferenteCadastroException erroEsperado = Assertions.
                assertThrows(CpfDiferenteCadastroException.class, () -> {

          Associado assosociado =
                  associadoService.buscarOuFalhar(1L);

            Voto novoVoto = new Voto();
            novoVoto.setPauta(pauta);
            novoVoto.setOpcaoVoto(true);
            novoVoto.setCpf("92834922268");
            novoVoto.setAssociado(assosociado);

          votoService.enviarVoto(pauta.getId(), sessao.getId(), novoVoto);

        });

        assertThat(erroEsperado).isNotNull();
      }

    @Test
    public void deveBuscarComSucessoVotosPorIdDaPauta() {

        List<Voto> votos = votoService.buscarVotosPorPautaId(pauta.getId());

        assertThat(votos).isNotEmpty();

        for(Voto v: votos) {
            assertThat(v.getId()).isNotNull();
        }
    }


    private static String geraCpf() {
        GeraCpf geraCpf = new GeraCpf();
        String cpfGerado = geraCpf.cpf();
        String cpfSemCaracteresEspeciais = geraCpf.removeCaracteresEspeciais(cpfGerado);
        return cpfSemCaracteresEspeciais;
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
         pautaService.salvar(novaPauta);

        Sessao novaSessao = new Sessao();
        novaSessao.setDescricao("Testes na api de votação");
        novaSessao.setTempoAbertura(60L);
        novaSessao.setPauta(novaPauta);

        sessaoService.iniciarSessaoVotacao(novaSessao);

        pauta = pautaService.buscarOuFalhar(1L);
        sessao = sessaoService.buscarOuFalhar(1L);

        Associado assosociado1 =
                associadoService.buscarOuFalhar(1L);

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

    }

}
