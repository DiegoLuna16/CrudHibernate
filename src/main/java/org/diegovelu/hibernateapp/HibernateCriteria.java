package org.diegovelu.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.diegovelu.hibernateapp.entity.Cliente;
import org.diegovelu.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateCriteria {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
        Root<Cliente> from = query.from(Cliente.class);
        query.select(from);
        List<Cliente> clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);
        System.out.println("======== listar where equals ========");

        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<String> nombreParam = cb.parameter(String.class, "nombre");
        query.select(from).where(cb.equal(from.get("nombre"), nombreParam));
        clientes = em.createQuery(query).setParameter("nombre", "Diego").getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== usando where like para buscar clientes por nombre ========");

        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<String> nombreParamLike = cb.parameter(String.class, "nombre");
        query.select(from).where(cb.like(cb.upper(from.get("nombre")), cb.upper(nombreParamLike)));
        clientes = em.createQuery(query).setParameter("nombre", "%di%").getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== usando where between para buscar entre rangos ========");
        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(cb.between(from.get("id"), 2L, 6L));
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== usando where in ========");
        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(from.get("nombre").in(List.of("Diego", "Rosy", "Carlos")));
        clientes = em.createQuery(query)
                .getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== filtrar usando predicados mayor que o mayor igual que ========");
        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(cb.ge(from.get("id"), 2L));
        clientes = em.createQuery(query)
                .getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== filtrar usando predicados mayor que========");
        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(cb.gt(cb.length(from.get("nombre")), 4L));
        clientes = em.createQuery(query)
                .getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== consulta con los predicados conjuncion y disyunción or ========");
        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        Predicate porNombre = cb.equal(from.get("nombre"), "Diego");
        Predicate porFormaPago = cb.equal(from.get("formaPago"), "paypal");
        Predicate porIdMayor = cb.gt(from.get("id"), 6L);
        query.select(from).where(cb.and(porIdMayor, cb.or(porNombre, porFormaPago)));
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== consulta por order by asc desc ========");
        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).orderBy(cb.asc(from.get("nombre")), cb.desc(from.get("apellido")));
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== consulta por id ========");
        query = cb.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(cb.equal(from.get("id"), 2L));
        Cliente cliente = em.createQuery(query).getSingleResult();
        System.out.println("cliente = " + cliente);

        System.out.println("======== consulta solo el nombre de los Clientes ========");

        CriteriaQuery<String> queryString = cb.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(from.get("nombre"));
        List<String> nombres = em.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======== consulta solo el nombre de los Clientes unicos con distinct ========");

        queryString = cb.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(cb.upper(from.get("nombre"))).distinct(true);
        nombres = em.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======== consulta por nombres y apellidos concatenados ========");
        queryString = cb.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(cb.concat(cb.concat(from.get("nombre"), " "), from.get("apellido")));
        nombres = em.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======== consulta por nombres y apellidos concatenados con lower o upper ========");
        queryString = cb.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(cb.upper(cb.concat(cb.concat(from.get("nombre"), " "), from.get("apellido"))));
        nombres = em.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);


        System.out.println("======== consulta por campos personalizados del entity Cliente ========");
        CriteriaQuery<Object[]> queryObject = cb.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido"));
        List<Object[]> objects = em.createQuery(queryObject).getResultList();
        objects.forEach(reg -> {
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id: " + id + " nombre: " + nombre + " apellido: " + apellido);
        });

        System.out.println("======== consulta por campos personalizados del entity Cliente con where ========");
        queryObject = cb.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(cb.like(from.get("nombre"), "%a%"));
        objects = em.createQuery(queryObject).getResultList();
        objects.forEach(reg -> {
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id: " + id + " nombre: " + nombre + " apellido: " + apellido);
        });

        System.out.println("======== consulta por campos personalizados del entity Cliente con where id ========");
        queryObject = cb.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(cb.equal(from.get("id"), 4L));
        Object[] objeto = em.createQuery(queryObject).getSingleResult();

        Long id = (Long) objeto[0];
        String nombre = (String) objeto[1];
        String apellido = (String) objeto[2];
        System.out.println("id: " + id + " nombre: " + nombre + " apellido: " + apellido);

        System.out.println("======== contar los registros con count ========");

        CriteriaQuery<Long> queryLong = cb.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(cb.count(from.get("id")));
        Long count = em.createQuery(queryLong).getSingleResult();
        System.out.println("count = " + count);

        System.out.println("======== sumar datos de algun campo de la tabla ========");

        queryLong = cb.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(cb.sum(from.get("id")));
        Long sum = em.createQuery(queryLong).getSingleResult();
        System.out.println("sum = " + sum);

        System.out.println("======== obtener el maximo id ========");

        queryLong = cb.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(cb.max(from.get("id")));
        Long max = em.createQuery(queryLong).getSingleResult();
        System.out.println("max = " + max);

        System.out.println("======== obtener el minimo id ========");

        queryLong = cb.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(cb.min(from.get("id")));
        Long min = em.createQuery(queryLong).getSingleResult();
        System.out.println("min = " + min);

        System.out.println("======== obtener varios resultados de las funciones de agregacion ========");

        queryObject = cb.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(cb.max(from.get("id"))
                , cb.count(from.get("id"))
                , cb.min(from.get("id"))
                , cb.sum(from.get("id")));

        objeto = em.createQuery(queryObject).getSingleResult();

            max = (Long) objeto[0];
            count = (Long) objeto[1];
            min = (Long) objeto[2];
            sum = (Long) objeto[3];

        System.out.println("max = " + max + " count = " + count + " min = " + min + " sum = " + sum);

        em.close();
    }
}
