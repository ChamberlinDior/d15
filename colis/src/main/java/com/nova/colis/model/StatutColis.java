package com.nova.colis.model;

public enum StatutColis {
    EN_ATTENTE,
    RECUPERE,                // Nouveau statut : le colis a été récupéré par le livreur
    EN_COURS_DE_LIVRAISON,
    LIVRE,
    ANNULE
}
