package com.nova.colis.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entité représentant un Livreur dans le système.
 * Permet de gérer l'inscription, la connexion, la photo,
 * et la localisation en temps réel.
 */
@Entity
@Table(name = "livreurs")
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

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
     * Photo en base64 ou binaire. Ici, on stocke en binaire (byte[]).
     * LONGBLOB = accepte de grandes tailles de fichiers.
     */
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    /**
     * Géolocalisation en temps réel du livreur.
     * Peut être mise à jour régulièrement.
     */
    private Double latitudeActuelle;
    private Double longitudeActuelle;

    /**
     * Role = "ROLE_LIVREUR" par défaut
     * (utilisé en Spring Security si besoin).
     */
    private String role;

    // --- Constructeurs ---

    public Livreur() {
    }

    public Livreur(String email, String password, String nom, String prenom, String telephone,
                   String adresse, String numeroPieceIdentite, byte[] photo,
                   Double latitudeActuelle, Double longitudeActuelle, String role) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.numeroPieceIdentite = numeroPieceIdentite;
        this.photo = photo;
        this.latitudeActuelle = latitudeActuelle;
        this.longitudeActuelle = longitudeActuelle;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // --- equals & hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livreur)) return false;
        Livreur livreur = (Livreur) o;
        return Objects.equals(id, livreur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
