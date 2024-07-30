package org.diegovelu.hibernateapp;

import jakarta.persistence.EntityManager;
import org.diegovelu.hibernateapp.entity.Cliente;
import org.diegovelu.hibernateapp.services.ClienteService;
import org.diegovelu.hibernateapp.services.ClienteServiceImpl;
import org.diegovelu.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class HibernateCrudService {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        ClienteService service = new ClienteServiceImpl(em);
        System.out.println("========== listar ==========");
        List<Cliente> clientes = service.listar();
        clientes.forEach(System.out::println);
        System.out.println("========== buscar ==========");
        Optional<Cliente> cliente = service.porId(1L);
        cliente.ifPresent(System.out::println);
        System.out.println("========== insertar nuevo cliente ==========");
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setNombre("Javier");
        clienteNuevo.setApellido("Luna");
        clienteNuevo.setFormaPago("paypal");
        service.guardar(clienteNuevo);
        System.out.println("Cliente guardado!");
        service.listar().forEach(System.out::println);
        System.out.println("========== editar cliente ==========");
        Long id = clienteNuevo.getId();
        cliente = service.porId(id);
        cliente.ifPresent(c -> {
            c.setFormaPago("mercado pago");
            service.guardar(c);
            System.out.println("Cliente editado!");
        });
        service.listar().forEach(System.out::println);
        System.out.println("========== eliminar cliente ==========");

        id = clienteNuevo.getId();
        cliente = service.porId(id);
        cliente.ifPresent(c -> {
            service.eliminar(c.getId());
        });

//        if(cliente.isPresent()) {
//            service.eliminar(id);
//        }
        service.listar().forEach(System.out::println);
        em.close();
    }
}
