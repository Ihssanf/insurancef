package com.gestion.assurance.entities;

import jakarta.persistence.*;

@Entity
public class Assurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Double montant;
    private String couverture;

    // Default constructor
    public Assurance() {}

    // Parameterized constructor
    public Assurance(String type, Double montant, String couverture) {
        this.type = type;
        this.montant = montant;
        this.couverture = couverture;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }
}