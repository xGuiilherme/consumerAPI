package restapi.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.consumer.entities.FilmeModel;

@Repository
public interface FilmeRepository extends JpaRepository<FilmeModel, Long> {

}
