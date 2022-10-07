package com.example.CrudVL.model.cliente;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClienteTelefone implements Serializable {

    private static final long serialVersionUID = 1768448193870878261L;

    @NotNull
    private String telefone;
    @NotNull
    private String descricao;

    public ClienteTelefone() {}

    public ClienteTelefone(String telefone, String descricao) {
        this.telefone = telefone;
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteTelefone that = (ClienteTelefone) o;
        return Objects.equals(telefone, that.telefone) && Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telefone, descricao);
    }

    @Override
    public String toString() {
        return telefone + " : " + descricao;
    }
}
