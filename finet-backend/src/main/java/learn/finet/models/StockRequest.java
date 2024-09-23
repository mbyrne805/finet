package learn.finet.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class StockRequest {

    @NotBlank(message = "Symbol is required.")
    @Size(max = 7, message = "Symbol must be 7 characters or less.")
    private String symbol;
    private Set<String> tags;
    private Set<String> relatedStocks;

    public StockRequest() {}

    public StockRequest(String symbol, Set<String> tags, Set<String> relatedStocks) {
        this.symbol = symbol;
        this.tags = tags;
        this.relatedStocks = relatedStocks;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getRelatedStocks() {
        return relatedStocks;
    }

    public void setRelatedStocks(Set<String> relatedStocks) {
        this.relatedStocks = relatedStocks;
    }
}