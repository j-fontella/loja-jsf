package com.example.CrudVL.services;

import com.example.CrudVL.exceptions.ClienteExistenteException;
import com.example.CrudVL.exceptions.NenhumTelefoneInformadoException;
import com.example.CrudVL.bean.cliente.Cliente;
import com.example.CrudVL.model.cliente.ClienteDAO;
import com.example.CrudVL.model.cliente.ClienteFilter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ClienteServicesImpl implements ClienteServices{

    @Inject
    private ClienteDAO clienteDao;

    @Override
    public Cliente save(Cliente cliente) throws ClienteExistenteException,
            NenhumTelefoneInformadoException {

        if (clienteDao.existe(cliente)) {
            throw new ClienteExistenteException();
        }
        if (cliente.getTelefones().size()== 0) {
            throw new NenhumTelefoneInformadoException();
        }
        return clienteDao.save(cliente);
    }

    @Override
    public void remove(Long id) {
        clienteDao.remove(id);
    }

    @Override
    public Cliente findById(Long id){
        return clienteDao.findById(id);
    }

    @Override
    public List<Cliente> findByFilter(ClienteFilter filter) {
        return clienteDao.findByFilter(filter);
    }


}
