package learn.finet.data;

import learn.finet.models.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends Neo4jRepository<Tag, Long> {

    @Query("MATCH (t:Tag) WHERE t.name = $name RETURN t LIMIT 1")
    Optional<Tag> findTagByName(String name);
}
