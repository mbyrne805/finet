package learn.finet.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class StockRequest {

    private Long id;

    @NotBlank(message = "Symbol is required.")
    @Size(max = 7, message = "Symbol must be 7 characters or less.")
    private String symbol;

    private Set<String> tags;
    private Set<String> relatedStocks;

    @JsonProperty("xPos")
    private Double xPos;

    @JsonProperty("yPos")
    private Double yPos;

    private String test;

    public StockRequest() {}

    public StockRequest(Long id, String symbol, Set<String> tags, Set<String> relatedStocks, Double xPos, Double yPos, String test) {
        this.id = id;
        this.symbol = symbol;
        this.tags = tags;
        this.relatedStocks = relatedStocks;
        this.xPos = xPos;
        this.yPos = yPos;
        this.test = test;
    }

    public Long getId() {
        return id;
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

    public Double getXPos() {
        return xPos;
    }

    public void setXPos(Double xPos) {
        this.xPos = xPos;
    }

    public Double getYPos() {
        return yPos;
    }

    public void setYPos(Double yPos) {
        this.yPos = yPos;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}