package br.com.schoolar.materia.model;

import br.com.schoolar.aluno.model.Aluno;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TB_MATERIA")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MATERIA")
    @SequenceGenerator(
            name = "SQ_MATERIA",
            sequenceName = "SQ_MATERIA",
            initialValue = 1,
            allocationSize = 1
    )
    @Column(name = "ID_MATERIA")
    private Long id;

    @Column(name = "NM_MATERIA")
    private String name;

    @Column(name = "NUM_AVERAGE")
    private BigDecimal average;

    @Column(name = "NUM_ABSENCES")
    private int absences;
}
