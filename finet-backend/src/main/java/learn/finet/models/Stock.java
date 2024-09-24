package learn.finet.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class Stock {

    public Stock() {
    }

    public Stock(String symbol, float xPos, float yPos) {
        this.symbol = symbol;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Id
    @GeneratedValue
    @Null(message = "Id must be null.")
    private Long id;

    @NotBlank(message = "Symbol is required.")
    @Size(max = 7, message = "Symbol must be 7 characters or less.")
    private String symbol;

    @Relationship(type = "HAS_TAG", direction = Relationship.Direction.OUTGOING)
    private Set<Tag> tags = Set.of();

    @Relationship(type = "RELATED_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Stock> stocks = Set.of();

    float xPos;
    float yPos;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                "symbol='" + symbol + '\'' +
                ", tags=" + tags +
                ", stocks=" + stocks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock stock)) return false;
        return symbol.equals(stock.symbol);
    }
}
