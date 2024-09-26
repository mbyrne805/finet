package learn.finet.domain;

import learn.finet.data.StockRepository;
import learn.finet.models.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class StockServiceTest {

    @MockBean
    StockRepository repository;

    @Autowired
    StockService service;

    @Test
    void shouldAdd() {
        Stock arg = new Stock();
        arg.setSymbol("AAPL");

        Stock expected = new Stock();
        expected.setSymbol("AAPL");

        when(repository.save(any())).thenReturn(expected);

        Result<Stock> actual = service.saveStock(arg, Set.of(), Set.of());
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<Stock> actual = service.saveStock(null, Set.of(), Set.of());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals(1, actual.getErrorMessages().size());
        assertEquals("Stock cannot be null.", actual.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddEmptySymbol() {
        Stock arg = new Stock();
        arg.setSymbol(" ");

        Result<Stock> actual = service.saveStock(arg, Set.of(), Set.of());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals(1, actual.getErrorMessages().size());
        assertEquals("Symbol is required.", actual.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddSymbolTooLong() {
        Stock arg = new Stock();
        arg.setSymbol("ABCDEFGHI");

        Result<Stock> actual = service.saveStock(arg, Set.of(), Set.of());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals(1, actual.getErrorMessages().size());
        assertEquals("Symbol must be 7 characters or less.", actual.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicateSymbol() {
        Stock arg = new Stock();
        arg.setSymbol("AAPL");

        Stock existing = new Stock();
        existing.setSymbol("AAPL");
        existing.setId(1L);

        when(repository.findStockBySymbol("AAPL")).thenReturn(Optional.of(existing));

        Result<Stock> actual = service.saveStock(arg, Set.of(), Set.of());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals(1, actual.getErrorMessages().size());
        assertEquals("Symbol must be unique.", actual.getErrorMessages().get(0));
    }
}