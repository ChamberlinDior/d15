package com.nova.colis.exception;

/**
 * Exception personnalisée pour les ressources non trouvées.
 */
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Constructeur de l'exception.
     *
     * @param resourceName Le nom de la ressource (ex. "Client").
     * @param fieldName    Le nom du champ (ex. "id").
     * @param fieldValue   La valeur du champ.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s non trouvé avec %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // Getters

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
