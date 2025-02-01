package com.nova.colis.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entité représentant un Client dans le système.
 */
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String nom;

    private String prenom;

    private String telephone;

    private String adresse;

    private Double latitude; // Latitude pour la géolocalisation
    private Double longitude; // Longitude pour la géolocalisation

    private String role; // Exemple: ROLE_CLIENT, ROLE_ADMIN, ROLE_LIVREUR

    /**
     * **Photo** en binaire (LONGBLOB) acceptant tout format/taille.
     */
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;  // Nouveau champ

    // --- Constructeurs

    public Client() {
    }

    public Client(Long id, String email, String password, String nom, String prenom, String telephone,
                  String adresse, Double latitude, Double longitude, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
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

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * **Getter/Setter de la photo**.
     */
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // equals et hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
