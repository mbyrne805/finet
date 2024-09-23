package learn.finet.controllers;

import jakarta.validation.Valid;
import learn.finet.domain.Result;
import learn.finet.domain.StockService;
import learn.finet.models.Stock;
import learn.finet.models.StockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static learn.finet.controllers.ResultToResponseEntity.toResponseEntity;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping()
    public List<Stock> findAll() {
        return stockService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> saveStock(
            @RequestBody @Valid StockRequest stockRequest,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Stock stock = new Stock(stockRequest.getSymbol());
        Set<String> tagNames = stockRequest.getTags();
        Set<String> relatedStockSymbols = stockRequest.getRelatedStocks();

        Result<?> saveResult = stockService.saveStock(stock, tagNames, relatedStockSymbols);
        return toResponseEntity(saveResult, HttpStatus.CREATED);
    }
}
