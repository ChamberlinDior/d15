package com.nova.colis.dto;

/**
 * DTO pour représenter les données d'un Client envoyées au frontend.
 */
public class ClientDTO {
    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private Double latitude;
    private Double longitude;
    private String role;
    private String fcmToken;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    /**
     * **Photo du client** (en binaire).
     */
    private byte[] photo; // Nouveau champ

    // Constructeurs

    public ClientDTO() {
    }

    public ClientDTO(Long id, String email, String nom, String prenom,
                     String telephone, String adresse,
                     Double latitude, Double longitude, String role) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.role = role;
    }

    // Getters et Setters

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

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * **Getter/Setter pour la photo**.
     */
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
