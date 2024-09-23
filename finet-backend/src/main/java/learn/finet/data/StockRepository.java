package learn.finet.data;

import learn.finet.models.Stock;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends Neo4jRepository<Stock, Long> {

    @Query("MATCH (s:Stock) WHERE s.symbol = $symbol RETURN s LIMIT 1")
    Optional<Stock> findStockBySymbol(String symbol);
}
