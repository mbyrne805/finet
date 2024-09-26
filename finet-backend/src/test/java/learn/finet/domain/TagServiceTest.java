package learn.finet.domain;

import learn.finet.data.TagRepository;
import learn.finet.models.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TagServiceTest {

    @MockBean
    TagRepository repository;

    @Autowired
    TagService service;

    @Test
    void shouldAdd() {
        Tag arg = new Tag();
        arg.setName("Test");

        Tag expected = new Tag();
        expected.setName("Test");

        when(repository.save(any())).thenReturn(expected);

        Result<Tag> actual = service.saveTag(arg);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<Tag> actual = service.saveTag(null);
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals(1, actual.getErrorMessages().size());
        assertEquals("Tag cannot be null.", actual.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddEmptyName() {
        Tag arg = new Tag();
        arg.setName(" ");

        Result<Tag> actual = service.saveTag(arg);
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals(1, actual.getErrorMessages().size());
        assertEquals("Name is required.", actual.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNameTooLong() {
        Tag arg = new Tag();
        arg.setName("This name is too long to be valid.");

        Result<Tag> actual = service.saveTag(arg);
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals(1, actual.getErrorMessages().size());
        assertEquals("Name must be 20 characters or less.", actual.getErrorMessages().get(0));
    }
}