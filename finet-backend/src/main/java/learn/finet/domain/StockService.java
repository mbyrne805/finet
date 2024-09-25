package learn.finet.domain;

import learn.finet.data.StockRepository;
import learn.finet.data.TagRepository;
import learn.finet.models.Stock;
import learn.finet.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TagRepository tagRepository;

    public StockService(StockRepository stockRepository, TagRepository tagRepository) {
        this.stockRepository = stockRepository;
        this.tagRepository = tagRepository;
    }

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Result<Stock> save(Stock stock) {
        Result<Stock> result = validate(stock);
        if (!result.isSuccess()) {
            return result;
        }

        Stock s = stockRepository.save(stock);
        result.setPayload(s);
        return result;
    }

    public Result<Stock> saveStock(Stock stock, Set<String> tagNames, Set<String> relatedStockSymbols) {
        Result<Stock> result = validate(stock);
        if (!result.isSuccess()) {
            return result;
        }

        Optional<Stock> existingStock = stockRepository.findStockBySymbol(stock.getSymbol());
        Stock stockToSave = existingStock.orElse(stock);

        Set<Tag> tags = new HashSet<>();
        if (tagNames != null) {
            for (String tagName : tagNames) {
                Optional<Tag> existingTag = tagRepository.findTagByName(tagName);
                Tag tag = existingTag.orElseGet(() -> new Tag(tagName));
                tags.add(tag);
            }
        }
        stockToSave.setTags(tags);

        Set<Stock> relatedStocks = new HashSet<>();
        if (relatedStockSymbols != null) {
            for (String relatedStockSymbol : relatedStockSymbols) {
                Optional<Stock> existingRelatedStock = stockRepository.findStockBySymbol(relatedStockSymbol);
                Stock relatedStock = existingRelatedStock.orElseGet(() -> new Stock(relatedStockSymbol));
                relatedStocks.add(relatedStock);
            }
        }
        stockToSave.setStocks(relatedStocks);

        stockToSave.setXPos(stock.getXPos());
        stockToSave.setYPos(stock.getYPos());

        Stock s = stockRepository.save(stockToSave);
        result.setPayload(s);
        return result;
    }

    private Result<Stock> validate(Stock stock) {
        Result<Stock> result = new Result<>();

        if (stock == null) {
            result.addErrorMessage("stock cannot be null");
            return result;
        }

        if (stock.getSymbol() == null || stock.getSymbol().isBlank()) {
            result.addErrorMessage("symbol is required");
        }

        if (stock.getSymbol().length() > 7) {
            result.addErrorMessage("symbol must be 7 characters or less");
        }

        Stock existing = stockRepository.findStockBySymbol(stock.getSymbol()).orElse(null);

        if (existing != null && !existing.getId().equals(stock.getId())) {
            result.addErrorMessage("symbol must be unique");
        }

        return result;
    }
}
