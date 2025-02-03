package com.nova.colis.controller;

import com.nova.colis.dto.ClientDTO;
import com.nova.colis.dto.ClientRequestDTO;
import com.nova.colis.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller pour gérer les opérations liées aux Clients.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Inscription d'un nouveau client.
     * Endpoint : POST /api/clients/register
     *
     * @param clientRequestDTO Les donn&eacute;es du client.
     * @return Le client inscrit.
     */
    @PostMapping("/register")
    public ResponseEntity<ClientDTO> registerClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientDTO clientDTO = clientService.registerClient(clientRequestDTO);
        return ResponseEntity.ok(clientDTO);
    }

    /**
     * Connexion d'un client.
     * Endpoint : POST /api/clients/login
     *
     * @param credentials L'email et le mot de passe du client.
     * @return L'utilisateur + un token si la connexion est réussie, ou un code 401 si échec.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginClient(@RequestBody ClientRequestDTO credentials) {
        // Appel de la méthode login dans ClientService
        ClientDTO clientDTO = clientService.login(credentials.getEmail(), credentials.getPassword());

        if (clientDTO == null) {
            // Identifiants invalides
            return ResponseEntity.status(401).body("Identifiants incorrects");
        }

        // Construire la réponse avec user + un token fictif (ou JWT si implémenté)
        Map<String, Object> response = new HashMap<>();
        response.put("user", clientDTO);
        response.put("token", "fake-jwt-token");

        return ResponseEntity.ok(response);
    }

    /**
     * Récupération d'un client par ID.
     * Endpoint : GET /api/clients/{id}
     *
     * @param id L'ID du client.
     * @return Les informations du client.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.getClientById(id);
        return ResponseEntity.ok(clientDTO);
    }

    /**
     * Récupération de tous les clients.
     * Endpoint : GET /api/clients
     *
     * @return La liste de tous les clients.
     */
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Mise à jour d'un client existant.
     * Endpoint : PUT /api/clients/{id}
     *
     * @param id                L'ID du client à mettre à jour.
     * @param clientRequestDTO  Les nouvelles données du client.
     * @return Le client mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id,
                                                  @Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientDTO updatedClient = clientService.updateClient(id, clientRequestDTO);
        return ResponseEntity.ok(updatedClient);
    }

    /**
     * Suppression d'un client.
     * Endpoint : DELETE /api/clients/{id}
     *
     * @param id L'ID du client à supprimer.
     * @return Réponse sans contenu.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
