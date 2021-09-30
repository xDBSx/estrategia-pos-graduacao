package br.estrategia.app.application.resource;

import br.estrategia.app.domain.model.entidade.Questao;
import br.estrategia.app.domain.repository.QuestaoRepository;
import br.estrategia.app.infra.rest.URI_API_PATHS;
import br.estrategia.app.util.AbstractAPITesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestaoResourceTest extends AbstractAPITesting {
	
	@Autowired
    private QuestaoRepository questaoRepository;

    @BeforeEach
    public void setUp() {
        web = web.mutate().responseTimeout(Duration.ofMillis(10000)).build();
        questaoRepository.deleteAll();
    }

    @Test
    public void salvar_questao() {
        Questao qt = new Questao();
        web.post().uri(URI_API_PATHS.QUESTOES_API).header("Authorization", TOKEN_ADMIN)
                .accept(MediaType.ALL).body(BodyInserters.fromValue(qt))
                .exchange().expectStatus().isCreated().expectBody(Questao.class)
                .value(c -> assertTrue(c.getId() > 0));
    }
    
    @Test
    public void atualizar_questao() {
        Questao qt = new Questao();
        qt.setTexto("questao 1");

        web.post().uri(URI_API_PATHS.QUESTOES_API)
                .accept(MediaType.ALL)
                .header("Authorization", TOKEN_ADMIN)
                .body(BodyInserters.fromValue(qt))
                .exchange()
                .expectStatus().isCreated().expectBody(Questao.class)
                .value(c -> {
                    assertEquals("questao 1", c.getTexto());
                    qt.setId(c.getId());
                    qt.setTexto("questao atualizada");

                    web.put().uri(URI_API_PATHS.QUESTOES_API + "/" + c.getId())
                            .accept(MediaType.ALL)
                            .header("Authorization", TOKEN_ADMIN)
                            .body(BodyInserters.fromValue(qt))
                            .exchange()
                            .expectStatus().isOk().expectBody(Questao.class)
                            .value(questaoAtualizada -> assertEquals("questao atualizada", questaoAtualizada.getTexto()));
                });

    }
    
    @Test
    public void remover_questao() {
        salvar_questao();
        conferirQuestoes(1);
        web.delete().uri(URI_API_PATHS.QUESTOES_API + "/1")
                .accept(MediaType.ALL)
                .header("Authorization", TOKEN_ADMIN)
                .exchange()
                .expectStatus().isOk();
        conferirQuestoes(0);
    }
    
    private void conferirQuestoes(int total) {
        web.get().uri(URI_API_PATHS.QUESTOES_API)
                .accept(MediaType.ALL)
                .header("Authorization", TOKEN_ADMIN)
                .exchange()
                .expectStatus().isOk().expectBodyList(Questao.class)
                .hasSize(total);
    }

}
