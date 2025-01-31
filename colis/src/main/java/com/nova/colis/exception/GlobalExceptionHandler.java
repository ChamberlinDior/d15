package com.nova.colis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Gestionnaire global des exceptions pour l'application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestion des exceptions ResourceNotFoundException.
     *
     * @param ex      L'exception.
     * @param request La requête web.
     * @return La réponse d'erreur.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Gestion des autres exceptions.
     *
     * @param ex      L'exception.
     * @param request La requête web.
     * @return La réponse d'erreur.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Classe interne pour les détails de l'erreur.
     */
    public static class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;

        /**
         * Constructeur de la classe ErrorDetails.
         *
         * @param timestamp La date et l'heure de l'erreur.
         * @param message    Le message d'erreur.
         * @param details    Les détails supplémentaires.
         */
        public ErrorDetails(Date timestamp, String message, String details) {
            super();
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        // Getters

        public Date getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getDetails() {
            return details;
        }
    }
}
