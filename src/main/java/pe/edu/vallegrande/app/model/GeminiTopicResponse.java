package pe.edu.vallegrande.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("gemini_topics")
public class GeminiTopicResponse {
    @Id
    @Column("id")
    private Long id;

    @Column("topic")
    private String topic;

    @Column("question")
    private String question;

    @Column("answer")
    private String answer;
}
