package br.com.schoolar.classroom.model;

import br.com.schoolar.aluno.model.Aluno;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "TB_CLASSROOM",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_NUM_CLASS", columnNames = "NUM_CLASS")
        }
)
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CLASSROOM")
    @SequenceGenerator(
            name = "SQ_CLASSROOM",
            sequenceName = "SQ_CLASSROOM",
            initialValue = 1,
            allocationSize = 1
    )
    @Column(name = "ID_CLASSROOM")
    private Long id;

    @Column(name = "NUM_CLASS")
    private String classNumber;

    @Column(name = "WEEKLY_LESSONS")
    private int weeklyLessons;

}
