package org.diegovelu.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.diegovelu.hibernateapp.entity.Cliente;
import org.diegovelu.hibernateapp.util.JpaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateCriteriaBusquedaBinaria {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Filtro para nombre");
        String nombre = sc.nextLine();

        System.out.println("Filtro para apellido");
        String apellido = sc.nextLine();

        System.out.println("Filtro para forma de pago");
        String formaPago = sc.nextLine();

        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> from = cq.from(Cliente.class);
        List<Predicate> predicates = new ArrayList<>();
        if (nombre != null && !nombre.isEmpty()) {
            predicates.add(cb.equal(from.get("nombre"), nombre));
        }
        if (apellido != null && !apellido.isEmpty()) {
            predicates.add(cb.equal(from.get("apellido"), apellido));
        }
        if (formaPago != null && !formaPago.isEmpty()) {
            predicates.add(cb.equal(from.get("formaPago"), formaPago));
        }

        cq.select(from).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        List<Cliente> clientes = em.createQuery(cq).getResultList();
        clientes.forEach(System.out::println);

    }
}
