package com.example.CrudVL.services;

import com.example.CrudVL.exceptions.ClienteExistenteException;
import com.example.CrudVL.exceptions.NenhumTelefoneInformadoException;
import com.example.CrudVL.bean.cliente.Cliente;
import com.example.CrudVL.model.cliente.ClienteFilter;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ClienteServices {

    Cliente save(Cliente cliente) throws ClienteExistenteException,
            NenhumTelefoneInformadoException;

    void remove(Long id);

    Cliente findById(Long id);

    List<Cliente> findByFilter(ClienteFilter filter);

}
