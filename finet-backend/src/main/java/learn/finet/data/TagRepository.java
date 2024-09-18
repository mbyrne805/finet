package learn.finet.data;

import learn.finet.models.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends Neo4jRepository<Tag, Long> {
}
