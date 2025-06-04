package locadora.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cliente")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente extends Pessoa implements Serializable {
    private String endereco;
    private String email;

    public Cliente(String nome, String cpf, String telefone, String endereco, String email, int id) {
        super(nome, cpf, telefone, id);
        this.endereco = endereco;
        this.email = email;
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

}
