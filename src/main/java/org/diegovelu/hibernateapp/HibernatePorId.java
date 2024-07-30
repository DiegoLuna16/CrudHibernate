package org.diegovelu.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.diegovelu.hibernateapp.entity.Cliente;
import org.diegovelu.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernatePorId {
    public static void main(String[] args) {
        //Otra Forma
//        Scanner s = new Scanner(System.in);
//        EntityManager em = JpaUtil.getEntityManager();
//        Query query = em.createQuery("select c from Cliente c where c.id=?1", Cliente.class);
//        System.out.println("Ingrese el id: ");
//        Long id = s.nextLong();
//        query.setParameter(1, id);
//        Cliente c = (Cliente) query.getSingleResult();
//        System.out.println(c);
//        em.close();

        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese el id: ");
        Long id = s.nextLong();
        EntityManager em = JpaUtil.getEntityManager();
        Cliente c = em.find(Cliente.class,id);
        System.out.println(c);
        Cliente c2 = em.find(Cliente.class,id);
        System.out.println(c2);
        em.close();
    }
}
