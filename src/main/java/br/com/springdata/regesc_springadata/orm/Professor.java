package br.com.springdata.regesc_springadata.orm;

import javax.persistence.*;
import java.util.List;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String prontuario;

    // colocar fetch = FetchType.EAGER para que não ocorra a exception
    // de LAZY para EAGER para que ele traga os dados da outra tabela(caso exista na consulta) junto a tabela principal
    // cascade = CascadeType.ALL para deletar tabelas que estão associadas a principal da consulta
    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private List<Disciplina> disciplinas;

    @Deprecated //para indicar que não será usada com frequencia
    public Professor(){}

    public Professor(String nome, String prontuario) {
        this.nome = nome;
        this.prontuario = prontuario;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @PreRemove
    public void atualizaDisciplinasOnRemove() {
        System.out.println("***** atualizaOnRemove *****");
        for (Disciplina disciplina : this.getDisciplinas()){
            disciplina.setProfessor(null);
        }
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prontuario='" + prontuario + '\'' +
                '}';
    }
}
