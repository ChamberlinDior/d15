package com.nova.colis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO pour représenter les données reçues lors des requêtes d'inscription ou de mise à jour d'un Client.
 */
public class ClientRequestDTO {

    @Email(message = "L'email doit être valide")
    private String email;

    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    private String nom;

    private String prenom;

    private String telephone;

    private String adresse;

    private Double latitude;
    private Double longitude;

    // Constructeurs

    public ClientRequestDTO() {
    }

    public ClientRequestDTO(String email, String password, String nom, String prenom, String telephone, String adresse, Double latitude, Double longitude) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters et Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
