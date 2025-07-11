package model;

public class Cliente extends Pessoa {
    private String endereco;
    private String email;

    public Cliente(String nome, String cpf, String telefone, String endereco, String email, int id) {
        super(nome, cpf, telefone, id);
        this.endereco = endereco;
        this.email = email;
    }

    public Cliente() {
        super();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Endereco: " + endereco +
                ", Email: " + email;
    }
}
