package br.edu.pucgoias.minhaapi.Interface;

import br.edu.pucgoias.minhaapi.Model.Contatos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends JpaRepository<Contatos, Long> {

    List<Contatos> findByNome(String nome);

    List<Contatos> findByEtiquetaNome(String etiqueta);

    List<Contatos> findByEtiquetaIsNotNull();

    List<Contatos> findByNomeContaining(String parteNome);
    
}