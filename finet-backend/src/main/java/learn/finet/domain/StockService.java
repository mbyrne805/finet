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

    public Stock getStock(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    public Result<Stock> saveStock(Stock stock) {
        Result<Stock> result = new Result<>();
        Stock s = stockRepository.save(stock);
        result.setPayload(s);
        return result;
    }
}
