package br.edu.pucgoias.minhaapi.Interface;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.pucgoias.minhaapi.Model.Etiquetas;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiquetas, Long> {

    List<Etiquetas> findByNome(String nome);

    List<Etiquetas> findByNomeContaining(String parteNome);
    
}
