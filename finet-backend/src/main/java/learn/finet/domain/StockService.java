package learn.finet.domain;

import learn.finet.data.StockRepository;
import learn.finet.models.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
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

        Stock existing = stockRepository.findBySymbol(stock.getSymbol());
        if (existing != null && !existing.getId().equals(stock.getId())) {
            result.addErrorMessage("symbol must be unique");
        }

        return result;
    }
}
