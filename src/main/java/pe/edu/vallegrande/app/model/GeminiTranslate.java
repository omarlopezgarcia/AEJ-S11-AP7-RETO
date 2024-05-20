package pe.edu.vallegrande.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("gemini_translate")
public class GeminiTranslate {
    @Id
    @Column("id")
    private Long id;

    @Column("languages")
    private String languages;

    @Column("entered_text")
    private String entered_text;

    @Column("translated_text")
    private String translated_text;
}
