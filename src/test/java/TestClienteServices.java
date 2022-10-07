import com.example.CrudVL.bean.cliente.Cliente;
import com.example.CrudVL.exceptions.ClienteExistenteException;
import com.example.CrudVL.exceptions.NenhumTelefoneInformadoException;
import com.example.CrudVL.model.cliente.ClienteDAO;
import com.example.CrudVL.model.cliente.ClienteFilter;
import com.example.CrudVL.model.cliente.ClienteTelefone;
import com.example.CrudVL.services.ClienteServices;
import com.example.CrudVL.services.ClienteServicesImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;




public class TestClienteServices {


    @Inject
    private ClienteServices clienteServices;

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap
                .create(WebArchive.class, "testCliente.war")
                .addClasses(Cliente.class, ClienteTelefone.class, ClienteFilter.class)
                .addClasses(ClienteExistenteException.class,
                        NenhumTelefoneInformadoException.class)
                .addClasses(ClienteServices.class, ClienteServicesImpl.class,
                        ClienteDAO.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"))
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml");
    }


    @Test
    public void testAdd() throws Exception {

        Long idCliente = clienteServices.save(getCliente()).getId();
        Cliente cliente = clienteServices.findById(idCliente);
        assertNotNull(cliente);
        assertEquals("Bill", cliente.getNome());
        assertEquals(new Date(), cliente.getDataNascimento());
        assertEquals("bill@site.com", cliente.getEmail());
        assertEquals(2, cliente.getTelefones().size());
    }

    private Cliente getCliente() {
        Cliente cliente = new Cliente("Bill", new Date(),
                "bill@site.com");
        cliente.addTelefone(new ClienteTelefone("19-1111-1111", "Residencial"));
        cliente.addTelefone(new ClienteTelefone("19-9111-1111", "Celular"));
        return cliente;
    }

}
