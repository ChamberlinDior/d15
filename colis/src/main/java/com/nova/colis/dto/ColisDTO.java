package com.nova.colis.dto;

import com.nova.colis.model.ModePaiement;
import com.nova.colis.model.StatutColis;
import com.nova.colis.model.StatutPaiement;
import com.nova.colis.model.TypeColis;
import java.time.LocalDateTime;

public class ColisDTO {

    private Long id;
    private String referenceColis;
    private TypeColis typeColis;
    private String description;
    private Double poids;
    private String dimensions;
    private Double valeurDeclaree;
    private Boolean assurance;

    private Long clientId;
    private String nomExpediteur;
    private String telephoneExpediteur;
    private String emailExpediteur;
    private String adresseEnlevement;
    private String villeDepart;

    private String nomDestinataire;
    private String telephoneDestinataire;
    private String emailDestinataire;
    private String adresseLivraison;
    private String villeDestination;

    private Long livreurId;
    private String nomLivreur;
    private String telephoneLivreur;

    private StatutColis statutColis;
    private LocalDateTime dateCreation;
    private LocalDateTime datePriseEnCharge;
    private LocalDateTime dateLivraisonEstimee;
    private LocalDateTime dateLivraisonEffective;

    private Double prixTotal;
    private Double fraisLivraison;
    private Double commissionPlateforme;
    private ModePaiement modePaiement;
    private StatutPaiement statutPaiement;

    private String historiqueSuivi;
    private String coordonneesGPS;
    private String preuveLivraison;

    /**
     * Nouveau champ pour renvoyer les informations spécifiques au paiement.
     */
    private String paiementInfo;

    public ColisDTO() {
    }

    // --- GETTERS / SETTERS ---

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

    public String getPaiementInfo() {
        return paiementInfo;
    }

    public void setPaiementInfo(String paiementInfo) {
        this.paiementInfo = paiementInfo;
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
