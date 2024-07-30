package org.diegovelu.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.diegovelu.hibernateapp.entity.Cliente;
import org.diegovelu.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateListResultWhere {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("select c from Cliente c where c.formaPago=?1", Cliente.class);
        System.out.println("Ingrese una forma de pago: ");
        String formaPago = s.nextLine();
        query.setParameter(1, formaPago);
//        query.setMaxResults(1);
        List<Cliente> c = query.getResultList();
        System.out.println(c);
        em.close();
    }
}
