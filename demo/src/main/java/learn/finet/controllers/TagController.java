package learn.finet.controllers;

import learn.finet.domain.Result;
import learn.finet.domain.StockService;
import learn.finet.domain.TagService;
import learn.finet.models.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static learn.finet.controllers.ResultToResponseEntity.toResponseEntity;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PutMapping("/{tagName}")
    public ResponseEntity<?> updateTag(@PathVariable String tagName, @RequestBody Tag tag) {
        if (!tagName.equals(tag.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<?> saveResult = tagService.saveTag(tag);
        return toResponseEntity(saveResult, HttpStatus.OK);
    }
}
