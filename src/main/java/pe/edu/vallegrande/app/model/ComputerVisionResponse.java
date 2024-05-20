package pe.edu.vallegrande.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

@Data
@Table("computervisionresponse")
public class ComputerVisionResponse {
    @Id
    private Long id;

    @Column("descripcion")
    private String description;

    @Column("tags")
    private List<String> tags;

    @Column("url_imagen")
    private String imageUrl;

    @Column("is_adult_content")
    private boolean isAdultContent;

    @Column("is_racy_content")
    private boolean isRacyContent;

    @Column("is_gory_content")
    private boolean isGoryContent;
}
