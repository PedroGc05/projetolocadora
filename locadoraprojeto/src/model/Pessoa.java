package model;

import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    public Pessoa() {
    }

    public Pessoa(String nome, String cpf, String telefone, int id) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
