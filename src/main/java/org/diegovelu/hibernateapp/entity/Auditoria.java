package org.diegovelu.hibernateapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable
public class Auditoria {
    @Column(name="creado_en")
    private LocalDateTime creadoEn;
    @Column(name="modificado_en")
    private LocalDateTime modificadoEn;

    @PrePersist
    public void prePersists(){
        System.out.println("prePersists");
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdates(){
        System.out.println("preUpdates");
        this.modificadoEn = LocalDateTime.now();
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getModificadoEn() {
        return modificadoEn;
    }

    public void setModificadoEn(LocalDateTime modificadoEn) {
        this.modificadoEn = modificadoEn;
    }
}
