package learn.finet.controllers;

import learn.finet.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static learn.finet.domain.ResultType.*;

public class ResultToResponseEntity {
    public static ResponseEntity<?> toResponseEntity(Result<?> result, HttpStatus successStatus) {
        return switch (result.getType()) {
            case SUCCESS -> new ResponseEntity<>(result.getPayload(), successStatus);
            case INVALID -> new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            case NOT_FOUND -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }
}
