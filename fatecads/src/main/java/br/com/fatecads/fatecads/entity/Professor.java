package br.com.fatecads.fatecads.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idProfessor;

    @Column(nullable = false, length = 40)
    private String nomeProfessor;

    @Column(nullable = false, length = 11)
    private String telefoneProfessor;

    @Column(nullable=false, length = 50)
    private String graduacaoProfessor;

    @Column(nullable = false)
    private String rmProfessor;
}
