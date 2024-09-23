package learn.finet.controllers;

import jakarta.validation.Valid;
import learn.finet.domain.Result;
import learn.finet.domain.StockService;
import learn.finet.models.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> saveStock(
            @RequestBody @Valid Stock stock,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<?> saveResult = stockService.save(stock);
        return toResponseEntity(saveResult, HttpStatus.CREATED);
    }
}
