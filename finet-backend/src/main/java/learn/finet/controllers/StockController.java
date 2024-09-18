package learn.finet.controllers;

import learn.finet.domain.Result;
import learn.finet.domain.StockService;
import learn.finet.models.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static learn.finet.controllers.ResultToResponseEntity.toResponseEntity;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<?> saveStock(@RequestBody Stock stock) {
        Result<?> result = stockService.saveStock(stock);
        return toResponseEntity(result, HttpStatus.CREATED);
    }
}
