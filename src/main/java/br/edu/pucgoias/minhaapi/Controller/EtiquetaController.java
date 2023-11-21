package br.edu.pucgoias.minhaapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.pucgoias.minhaapi.Model.Etiquetas;
import br.edu.pucgoias.minhaapi.Interface.EtiquetaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cadastarEtiquetas")
public class EtiquetaController {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @GetMapping
    public List<Etiquetas> getAllEtiquetas() {
        return etiquetaRepository.findAll();
    }

    @PostMapping
    public Etiquetas createEtiqueta(@RequestBody Etiquetas etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Etiquetas> getEtiquetaById(@PathVariable Long id) {
        Optional<Etiquetas> etiqueta = etiquetaRepository.findById(id);

        return etiqueta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Etiquetas> editEtiquetaById(@PathVariable Long id, @RequestBody Etiquetas novaEtiqueta) {
        Optional<Etiquetas> etiquetaExistente = etiquetaRepository.findById(id);

        if (etiquetaExistente.isPresent()) {
            Etiquetas etiqueta = etiquetaExistente.get();
            etiqueta.setNome(novaEtiqueta.getNome());

            Etiquetas etiquetaAtualizada = etiquetaRepository.save(etiqueta);
            return ResponseEntity.ok(etiquetaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtiquetaById(@PathVariable Long id) {
        Optional<Etiquetas> etiquetaExistente = etiquetaRepository.findById(id);

        if (etiquetaExistente.isPresent()) {
            etiquetaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 
    @GetMapping("/buscarPorNome/{nome}")
    public List<Etiquetas> getEtiquetasByNome(@PathVariable String nome) {
        return etiquetaRepository.findByNome(nome);
    }

   
    @GetMapping("/contarEtiquetas")
    public Long contarEtiquetas() {
        return etiquetaRepository.count();
    }

 
    @GetMapping("/buscarPorParteDoNome/{parteNome}")
    public List<Etiquetas> getEtiquetasPorParteDoNome(@PathVariable String parteNome) {
        return etiquetaRepository.findByNomeContaining(parteNome);
    }

    

}
