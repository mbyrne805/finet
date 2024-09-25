package learn.finet.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Tag {

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(Long id, String name, Double xPos, Double yPos) {
        this.id = id;
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Id
    @GeneratedValue
    @Null(message = "Id must be null.")
    private Long id;

    @NotBlank(message = "Name is required.")
    @Size(max = 20, message = "Name must be 20 characters or less.")
    private String name;

    @JsonProperty("xPos")
    private Double xPos;

    @JsonProperty("yPos")
    private Double yPos;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getXPos() {
        return this.xPos;
    }

    public void setXPos(Double xPos) {
        this.xPos = xPos;
    }

    public Double getYPos() {
        return this.yPos;
    }

    public void setYPos(Double yPos) {
        this.yPos = yPos;
    }
}
