package org.diegovelu.hibernateapp;

import jakarta.persistence.EntityManager;
import org.diegovelu.hibernateapp.entity.Cliente;
import org.diegovelu.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateCrear {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre");
            String apellido = JOptionPane.showInputDialog("Ingrese el apellido");
            String formaPago = JOptionPane.showInputDialog("Ingrese la forma de Pago");

            em.getTransaction().begin();
            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setApellido(apellido);
            c.setFormaPago(formaPago);
            em.persist(c);
            em.getTransaction().commit();
            System.out.println("El id del cliente registrado es: " + c.getId());
            em.find(Cliente.class, c.getId());
            System.out.println(c);
        } catch (Exception e){
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
