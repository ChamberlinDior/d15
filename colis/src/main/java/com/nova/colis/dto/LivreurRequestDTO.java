package com.nova.colis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la création/mise à jour d'un Livreur.
 * Inclut la pièce d'identité, la photo en binaire (ou base64),
 * ainsi que la géolocalisation en temps réel.
 */
public class LivreurRequestDTO {

    @Email(message = "L'email doit être valide")
    private String email;

    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;

    /**
     * Numéro de pièce d'identité (CNI, passeport, etc.)
     * pour retrouver le livreur en cas de problèmes légaux.
     */
    private String numeroPieceIdentite;

    /**
     * Photo en binaire (ou base64). On reçoit ici un tableau de bytes.
     */
    private byte[] photo;

    /**
     * Géolocalisation en temps réel.
     */
    private Double latitudeActuelle;
    private Double longitudeActuelle;

    // --- Constructeur par défaut ---
    public LivreurRequestDTO() {
    }

    // --- Getters / Setters ---

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

    public String getNumeroPieceIdentite() {
        return numeroPieceIdentite;
    }

    public void setNumeroPieceIdentite(String numeroPieceIdentite) {
        this.numeroPieceIdentite = numeroPieceIdentite;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Double getLatitudeActuelle() {
        return latitudeActuelle;
    }

    public void setLatitudeActuelle(Double latitudeActuelle) {
        this.latitudeActuelle = latitudeActuelle;
    }

    public Double getLongitudeActuelle() {
        return longitudeActuelle;
    }

    public void setLongitudeActuelle(Double longitudeActuelle) {
        this.longitudeActuelle = longitudeActuelle;
    }
}
