package com.example.CrudVL.bean.cliente;

import com.example.CrudVL.model.cliente.ClienteTelefone;
import com.example.CrudVL.validation.email.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cliente", schema = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 7549185160123293775L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    private String nome;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Email
    @Column(unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cliente_telefone", schema = "cliente", joinColumns =
    @JoinColumn(name = "id_cliente"))
    private Set<ClienteTelefone> telefones;


    public Cliente(){

    }

    public Cliente(String nome, Date dataNascimento, String email) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
    }

    public boolean addTelefone(ClienteTelefone clienteTelefone) {
        if (telefones == null) {
            telefones = new HashSet<>();
        }
        return telefones.add(clienteTelefone);
    }
    public Set<ClienteTelefone> getTelefones() {
        if (telefones == null) {
            telefones = new HashSet<>();
        }
        return telefones;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public String bih() {
        String str = "";
        if (id != null) {
            str += "ID: " + id;
        }
        if (nome != null) {
            str += " NOME: " + nome;
        }
        return str;
    }
}
