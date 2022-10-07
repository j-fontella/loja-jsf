package com.example.CrudVL.bean.cliente;

import com.example.CrudVL.exceptions.ClienteExistenteException;
import com.example.CrudVL.exceptions.NenhumTelefoneInformadoException;
import com.example.CrudVL.model.cliente.ClienteFilter;
import com.example.CrudVL.model.cliente.ClienteTelefone;
import com.example.CrudVL.services.ClienteServices;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class ClienteMB implements Serializable {

    private static final long serialVersionUID = 6920835789315490285L;

    @SuppressWarnings("CdiUnproxyableBeanTypesInspection")
    @Inject
    private ClienteServices clienteServices;

    private Cliente cliente = new Cliente();

    private Long idCliente;

    private List<Cliente> clientes;

    private String nomeBuscar;

    private ClienteTelefone telefoneAdd = new ClienteTelefone();

    private List<ClienteTelefone> telefones;

    // bundle com as mensagens internacionalizaveis
    private ResourceBundle bundle;

    @PostConstruct
    public void init() {
        this.bundle = ResourceBundle.getBundle("messages", getFacesContext()
                .getViewRoot().getLocale());
        clientes = getClientes();
    }
    /**
     * Devolve a lista de clientes a ser mostrada no dataTable
     *
     * @return
     */
    public List<Cliente> getClientes() {
        if (clientes == null) {
            clientes = clienteServices.findByFilter(new ClienteFilter(nomeBuscar));
        }

        return clientes;
    }


    public void load() {
        if (!getFacesContext().isPostback()) {
            if (idCliente != null) {
                cliente = clienteServices.findById(idCliente);
                if (cliente == null) {
                    redirect("list.xhtml");
                }
            }
            else {
                cliente = new Cliente();
            }
        }
    }
    public String save() {
        try {
            clienteServices.save(cliente);
            addMessageFromBundleInFlash(FacesMessage.SEVERITY_INFO,
                    "label.cliente.salvo");
            limpar();
            return "list?faces-redirect=true";
        }
        catch (ClienteExistenteException e) {
            getFacesContext().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
                            .getString("label.cliente-existente")));
        }
        catch (NenhumTelefoneInformadoException e1) {
            getFacesContext().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
                            .getString("label.nenhum-telefone-informado")));
        }
        return null;
    }
    public String remove() {
        clienteServices.remove(cliente.getId());
        addMessageFromBundleInFlash(FacesMessage.SEVERITY_INFO,
                "label.cliente.excluido");
        limpar();
        return "list?faces-redirect=true";
    }

    public void buscar() {
        limpar();
    }

    public String cancelar() {
        limpar();
        return "list?faces-redirect=true";
    }

    public void addTelefone() {
        if (telefoneAdd.getTelefone() == null || telefoneAdd.getTelefone().isEmpty())
            getFacesContext().addMessage(
                    "formCliente:txtTelefone",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
                            .getString("label.telefone-nao-informado")));


        if (telefoneAdd.getTelefone() == null || telefoneAdd.getDescricao().isEmpty()) {
        getFacesContext().addMessage(
                "formCliente:txtDescricao",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
                        .getString("label.descricao-nao-informada")));
        }
    if (getFacesContext().getMessageList().size() == 0) {
        if (cliente.addTelefone(telefoneAdd)) {
            telefoneAdd = new ClienteTelefone();
            telefones = null;
        }
        else {
            getFacesContext().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
                            .getString("label.telefone-existente")));
        }
    }
}
    public void removeTelefone(ClienteTelefone clienteTelefone) {
        cliente.getTelefones().remove(clienteTelefone);
        telefones = null;
    }
    //alguns metodos getters e setters omitidos
    public List<ClienteTelefone> getTelefones() {
        if (telefones == null) {
            telefones = new ArrayList<ClienteTelefone>(cliente.getTelefones());
        }
        return telefones;
    }
    /**
     * Faz o redirecionamento para uma pagina (caminho relativo)
     *
     * @param pagina
     */
    private void redirect(String pagina) {
        try {
            getFacesContext().getExternalContext().redirect(pagina);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessageFromBundleInFlash(FacesMessage.Severity severidade, String chave) {
        Flash flash = getFacesContext().getExternalContext().getFlash();
        flash.setKeepMessages(true);
        getFacesContext().addMessage(null,
                new FacesMessage(severidade, null, bundle.getString(chave)));
    }
    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    private void limpar() {
        idCliente = null;
        cliente = new Cliente();
        clientes = null;
        telefoneAdd = new ClienteTelefone();
        telefones = null;
    }

    public ClienteServices getClienteServices() {
        return clienteServices;
    }

    public void setClienteServices(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getNomeBuscar() {
        return nomeBuscar;
    }

    public void setNomeBuscar(String nomeBuscar) {
        this.nomeBuscar = nomeBuscar;
    }

    public ClienteTelefone getTelefoneAdd() {
        return telefoneAdd;
    }

    public void setTelefoneAdd(ClienteTelefone telefoneAdd) {
        this.telefoneAdd = telefoneAdd;
    }

    public void setTelefones(List<ClienteTelefone> telefones) {
        this.telefones = telefones;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
}
