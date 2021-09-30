package br.estrategia.app.application.resource;

import br.estrategia.app.application.resource.suporte.AbstractRestBaseController;
import br.estrategia.app.domain.model.entidade.Questao;
import br.estrategia.app.domain.repository.QuestaoRepository;
import br.estrategia.app.infra.rest.URI_API_PATHS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = URI_API_PATHS.QUESTOES_API)
public class QuestaoResource extends AbstractRestBaseController<Questao, Long> {
	
	@Autowired
	private QuestaoRepository questaoRepository;

	@Override
	public CrudRepository<Questao, Long> getRepositorio() {
		return questaoRepository;
	}

}
