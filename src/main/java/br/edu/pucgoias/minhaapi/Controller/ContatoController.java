package br.edu.pucgoias.minhaapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import br.edu.pucgoias.minhaapi.Model.Contatos;
import br.edu.pucgoias.minhaapi.Model.Etiquetas;
import jakarta.transaction.Transactional;
import br.edu.pucgoias.minhaapi.Interface.ContatoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cadastro")
public class ContatoController {

    @Autowired
    private ContatoRepository ContatoRepository;

    @GetMapping
    public List<Contatos> getAllContatos() {
        return ContatoRepository.findAll();
    }

    @PostMapping
    public Contatos createContato(@RequestBody Contatos contato) {
        return ContatoRepository.save(contato);
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<Contatos> editContato(@PathVariable Long id, @RequestBody Contatos novoContato) {
        Optional<Contatos> contatoExistente = ContatoRepository.findById(id);

        if (contatoExistente.isPresent()) {
            Contatos contato = contatoExistente.get();
            contato.setNome(novoContato.getNome());
            contato.setNumero(novoContato.getNumero());

            // olha se tem etiqueta
            if (novoContato.getEtiqueta() != null) {
                // atualiza ela
                contato.setEtiqueta(novoContato.getEtiqueta());
            } else {
                // se o contato nao tiver etiqueta. defina
                contato.setEtiqueta(null);
            }

            Contatos contatoAtualizado = ContatoRepository.save(contato);
            return ResponseEntity.ok(contatoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deleteContatoById(@PathVariable Long id) {
        Optional<Contatos> contatoExistente = ContatoRepository.findById(id);

        if (contatoExistente.isPresent()) {
            ContatoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Contatos> getContatoById(@PathVariable Long id) {
        Optional<Contatos> contato = ContatoRepository.findById(id);

        return contato.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorNome/{nome}")
    public List<Contatos> getContatosByNome(@PathVariable String nome) {
        return ContatoRepository.findByNome(nome);
    }

    @GetMapping("/buscarPorEtiqueta/{etiqueta}")
    public List<Contatos> getContatosByEtiqueta(@PathVariable String etiqueta) {
        return ContatoRepository.findByEtiquetaNome(etiqueta);
    }

    @GetMapping("/BuscarTodoscomEtiqueta")
    public List<Contatos> getContatosComEtiqueta() {
        return ContatoRepository.findByEtiquetaIsNotNull();
    }

    @GetMapping("/contarContatos")
    public Long contarContatos() {
        return ContatoRepository.count();
    }

    @GetMapping("/buscarPorParteDoNome/{parteNome}")
    public List<Contatos> getContatosPorParteDoNome(@PathVariable String parteNome) {
        return ContatoRepository.findByNomeContaining(parteNome);
    }

    @PatchMapping("/atualizarNumero/{id}")
    public ResponseEntity<Contatos> atualizarNumeroDeContato(@PathVariable Long id, @RequestParam String novoNumero) {
        Optional<Contatos> contatoExistente = ContatoRepository.findById(id);

        if (contatoExistente.isPresent()) {
            Contatos contato = contatoExistente.get();
            contato.setNumero(novoNumero);

            Contatos contatoAtualizado = ContatoRepository.save(contato);
            return ResponseEntity.ok(contatoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @DeleteMapping("/removerEtiquetaDeContato/{contatoId}")
    public ResponseEntity<Void> removerEtiquetaDeContato(@PathVariable Long contatoId) {
        Optional<Contatos> contatoExistente = ContatoRepository.findById(contatoId);

        if (contatoExistente.isPresent()) {
            Contatos contato = contatoExistente.get();

            // defini o campo a etiqueta como nulo
            contato.setEtiqueta(null);

            // salva o contato atualizado
            ContatoRepository.save(contato);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/adicionarEtiquetaAContato/{contatoId}")
    @Transactional
    public ResponseEntity<?> adicionarEtiquetaAContato(@PathVariable Long contatoId, @RequestBody Etiquetas etiqueta) {
        Optional<Contatos> contatoExistente = ContatoRepository.findById(contatoId);

        if (contatoExistente.isPresent()) {
            Contatos contato = contatoExistente.get();

            // verifica se ja tem etiqueta
            if (contato.getEtiqueta() == null) {
                // Associe a etiqueta ao contato
                contato.setEtiqueta(etiqueta);

                // salva o cantato no banco
                Contatos contatoAtualizado = ContatoRepository.save(contato);
                return ResponseEntity.ok(contatoAtualizado);
            } else {

                String mensagemDeErro = "A etiqueta já está associada a este contato.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
            }
        } else {

            String mensagemDeErro = "Contato não encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemDeErro);
        }
    }

}
