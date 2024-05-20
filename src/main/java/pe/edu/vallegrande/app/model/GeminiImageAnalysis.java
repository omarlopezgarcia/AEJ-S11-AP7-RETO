package pe.edu.vallegrande.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Table("gemini_analisis_image")
public class GeminiImageAnalysis {
    @Id
    @Column("id")
    private Long id;

    @Column("imgbbUrl")
    private String imgbbUrl;

    @Column("geminiText")
    private String geminiText;

    @Column("message")
    private String message;

    @Column("nombreLanguage")
    private String nombreLanguage;
}