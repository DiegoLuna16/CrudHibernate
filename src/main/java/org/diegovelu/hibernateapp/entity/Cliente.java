package org.diegovelu.hibernateapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(name="forma_pago")
    private String formaPago;

    @Embedded
    private Auditoria auditoria = new Auditoria();



    public Cliente() {
    }

    public Cliente(Long id, String nombre, String apellido, String formaPago) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.formaPago = formaPago;
    }

    public Cliente(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }



    @Override
    public String toString() {
        LocalDateTime creado = this.auditoria != null ? auditoria.getCreadoEn():null;
        LocalDateTime modificado = this.auditoria != null ? auditoria.getModificadoEn():null;
        return "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", formaPago='" + formaPago + '\'' +
                ", creadoEn=" + creado + '\'' +
                ", modificadoEn=" + modificado
                ;
    }
}
