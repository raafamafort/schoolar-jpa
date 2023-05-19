package br.com.schoolar.aluno.model;

import br.com.schoolar.classroom.model.Classroom;
import br.com.schoolar.materia.model.Materia;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(
        name = "TB_ALUNO",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_NUM_RM", columnNames = "NUM_RM"),
                @UniqueConstraint(name = "UK_DS_EMAIL", columnNames = "DS_EMAIL")
        }
)
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ALUNO")
    @SequenceGenerator(
            name = "SQ_ALUNO",
            sequenceName = "SQ_ALUNO",
            initialValue = 1,
            allocationSize = 1
    )
    @Column(name = "ID_ALUNO")
    private Long id;

    @Column(name = "NM_ALUNO")
    private String name;

    @Column(name = "NUM_RM")
    private String rm;

    @Column(name = "DS_EMAIL")
    private String email;

    @Column(name = "DS_PASSWORD")
    private String password;

    @Column(name = "DT_BIRTHDATE")
    private LocalDate birthDate;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_MATERIAS",
            joinColumns = {
                    @JoinColumn(
                            name = "ID_ALUNO",
                            referencedColumnName = "ID_ALUNO",
                            foreignKey = @ForeignKey(name = "FK_ALUNO_MATERIA")
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "ID_MATERIA",
                            referencedColumnName = "ID_MATERIA",
                            foreignKey = @ForeignKey(name = "FK_MATERIA_ALUNO")
                    )
            }
    )
    private Set<Materia> materias = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ID_CLASSROOM",
            referencedColumnName = "ID_CLASSROOM",
            foreignKey = @ForeignKey(
                    name = "FK_ALUNO_CLASSROOM",
                    value = ConstraintMode.CONSTRAINT
            )
    )
    private Classroom classroom;

    public Aluno addMateria(Materia materia) {

        this.materias.add(materia);

        return this;
    }

}