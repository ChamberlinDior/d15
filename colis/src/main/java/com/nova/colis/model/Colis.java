package com.nova.colis.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité JPA représentant un Colis.
 */
@Entity
@Table(name = "colis")
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String referenceColis;       // Référence unique pour le suivi

    @Enumerated(EnumType.STRING)
    private TypeColis typeColis;         // STANDARD, OBJET_DE_VALEUR, VOLUMINEUX

    private String description;
    private Double poids;               // en kg
    private String dimensions;          // "Longueur x Largeur x Hauteur"
    private Double valeurDeclaree;      // valeur financière du colis

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean assurance;          // Oui/Non

    // Informations Expéditeur (client)
    private Long clientId;              // référence à l'ID du Client
    private String nomExpediteur;
    private String telephoneExpediteur;
    private String emailExpediteur;
    private String adresseEnlevement;
    private String villeDepart;

    // Informations Destinataire
    private String nomDestinataire;
    private String telephoneDestinataire;
    private String emailDestinataire;
    private String adresseLivraison;
    private String villeDestination;

    // Informations livreur
    private Long livreurId;
    private String nomLivreur;
    private String telephoneLivreur;

    // État du colis
    @Enumerated(EnumType.STRING)
    private StatutColis statutColis;    // EN_ATTENTE, EN_COURS_DE_LIVRAISON, LIVRE, ANNULE
    private LocalDateTime dateCreation;
    private LocalDateTime datePriseEnCharge;
    private LocalDateTime dateLivraisonEstimee;
    private LocalDateTime dateLivraisonEffective;

    // Tarification & Paiement
    private Double prixTotal;
    private Double fraisLivraison;
    private Double commissionPlateforme;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;
    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    // Suivi du colis
    @Lob
    @Column(columnDefinition = "TEXT")
    private String historiqueSuivi;      // Ex : JSON ou texte
    @Column(name = "coordonnees_gps")
    private String coordonneesGPS;
    // Position du livreur
    private String preuveLivraison;      // Photo, signature...

    /**
     * Constructeur par défaut.
     * Initialise le statut du colis à EN_ATTENTE et le statutPaiement à EN_ATTENTE.
     */
    public Colis() {
        this.dateCreation = LocalDateTime.now();
        this.statutColis = StatutColis.EN_ATTENTE;
        this.statutPaiement = StatutPaiement.EN_ATTENTE;
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceColis() {
        return referenceColis;
    }

    public void setReferenceColis(String referenceColis) {
        this.referenceColis = referenceColis;
    }

    public TypeColis getTypeColis() {
        return typeColis;
    }

    public void setTypeColis(TypeColis typeColis) {
        this.typeColis = typeColis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Double getValeurDeclaree() {
        return valeurDeclaree;
    }

    public void setValeurDeclaree(Double valeurDeclaree) {
        this.valeurDeclaree = valeurDeclaree;
    }

    public Boolean getAssurance() {
        return assurance;
    }

    public void setAssurance(Boolean assurance) {
        this.assurance = assurance;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getNomExpediteur() {
        return nomExpediteur;
    }

    public void setNomExpediteur(String nomExpediteur) {
        this.nomExpediteur = nomExpediteur;
    }

    public String getTelephoneExpediteur() {
        return telephoneExpediteur;
    }

    public void setTelephoneExpediteur(String telephoneExpediteur) {
        this.telephoneExpediteur = telephoneExpediteur;
    }

    public String getEmailExpediteur() {
        return emailExpediteur;
    }

    public void setEmailExpediteur(String emailExpediteur) {
        this.emailExpediteur = emailExpediteur;
    }

    public String getAdresseEnlevement() {
        return adresseEnlevement;
    }

    public void setAdresseEnlevement(String adresseEnlevement) {
        this.adresseEnlevement = adresseEnlevement;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getNomDestinataire() {
        return nomDestinataire;
    }

    public void setNomDestinataire(String nomDestinataire) {
        this.nomDestinataire = nomDestinataire;
    }

    public String getTelephoneDestinataire() {
        return telephoneDestinataire;
    }

    public void setTelephoneDestinataire(String telephoneDestinataire) {
        this.telephoneDestinataire = telephoneDestinataire;
    }

    public String getEmailDestinataire() {
        return emailDestinataire;
    }

    public void setEmailDestinataire(String emailDestinataire) {
        this.emailDestinataire = emailDestinataire;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getVilleDestination() {
        return villeDestination;
    }

    public void setVilleDestination(String villeDestination) {
        this.villeDestination = villeDestination;
    }

    public Long getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(Long livreurId) {
        this.livreurId = livreurId;
    }

    public String getNomLivreur() {
        return nomLivreur;
    }

    public void setNomLivreur(String nomLivreur) {
        this.nomLivreur = nomLivreur;
    }

    public String getTelephoneLivreur() {
        return telephoneLivreur;
    }

    public void setTelephoneLivreur(String telephoneLivreur) {
        this.telephoneLivreur = telephoneLivreur;
    }

    public StatutColis getStatutColis() {
        return statutColis;
    }

    public void setStatutColis(StatutColis statutColis) {
        this.statutColis = statutColis;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDatePriseEnCharge() {
        return datePriseEnCharge;
    }

    public void setDatePriseEnCharge(LocalDateTime datePriseEnCharge) {
        this.datePriseEnCharge = datePriseEnCharge;
    }

    public LocalDateTime getDateLivraisonEstimee() {
        return dateLivraisonEstimee;
    }

    public void setDateLivraisonEstimee(LocalDateTime dateLivraisonEstimee) {
        this.dateLivraisonEstimee = dateLivraisonEstimee;
    }

    public LocalDateTime getDateLivraisonEffective() {
        return dateLivraisonEffective;
    }

    public void setDateLivraisonEffective(LocalDateTime dateLivraisonEffective) {
        this.dateLivraisonEffective = dateLivraisonEffective;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public Double getCommissionPlateforme() {
        return commissionPlateforme;
    }

    public void setCommissionPlateforme(Double commissionPlateforme) {
        this.commissionPlateforme = commissionPlateforme;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public StatutPaiement getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(StatutPaiement statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public String getHistoriqueSuivi() {
        return historiqueSuivi;
    }

    public void setHistoriqueSuivi(String historiqueSuivi) {
        this.historiqueSuivi = historiqueSuivi;
    }

    public String getCoordonneesGPS() {
        return coordonneesGPS;
    }

    public void setCoordonneesGPS(String coordonneesGPS) {
        this.coordonneesGPS = coordonneesGPS;
    }

    public String getPreuveLivraison() {
        return preuveLivraison;
    }

    public void setPreuveLivraison(String preuveLivraison) {
        this.preuveLivraison = preuveLivraison;
    }
}
