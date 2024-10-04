package learn.finet.domain;

import learn.finet.data.StockRepository;
import learn.finet.data.TagRepository;
import learn.finet.models.Stock;
import learn.finet.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Result<Tag> saveTag(Tag tag) {
        Result<Tag> result = validate(tag);
        if (!result.isSuccess()) {
            return result;
        }

        Optional<Tag> existingTag = tagRepository.findTagByName(tag.getName());

        if (existingTag.isPresent()) {
            Tag existing = existingTag.get();
            existing.setXPos(tag.getXPos());
            existing.setYPos(tag.getYPos());
            tag = existing;
        }

        Tag savedTag = tagRepository.save(tag);
        result.setPayload(savedTag);
        return result;
    }

    private Result<Tag> validate(Tag tag) {
        Result<Tag> result = new Result<>();
        if (tag == null) {
            result.addErrorMessage("Tag cannot be null.");
            return result;
        }

        if (tag.getName() == null || tag.getName().isBlank()) {
            result.addErrorMessage("Name is required.");
        }

        if (tag.getName() != null && tag.getName().length() > 20) {
            result.addErrorMessage("Name must be 20 characters or less.");
        }

        return result;
    }
}
