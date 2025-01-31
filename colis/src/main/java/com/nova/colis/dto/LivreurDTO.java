package com.nova.colis.dto;

/**
 * DTO représentant un Livreur exposé côté frontend.
 * Inclut toutes les informations nécessaires pour afficher
 * ou manipuler les données du livreur (photo, identité, etc.).
 */
public class LivreurDTO {

    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;

    /**
     * Numéro de pièce d'identité (ex : CNI, passeport),
     * utile pour identifier formellement un livreur.
     */
    private String numeroPieceIdentite;

    /**
     * Photo du livreur en binaire (ou base64).
     * Permet d’afficher ou de télécharger son image de profil.
     */
    private byte[] photo;

    /**
     * Coordonnées GPS permettant de géolocaliser le livreur
     * en temps réel.
     */
    private Double latitudeActuelle;
    private Double longitudeActuelle;

    /**
     * Rôle attribué au livreur, en principe "ROLE_LIVREUR".
     * Peut être utile en Spring Security.
     */
    private String role;

    // --- Constructeur par défaut ---
    public LivreurDTO() {
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
