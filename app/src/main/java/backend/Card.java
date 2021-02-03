package backend;

import java.util.Date;

/**
 * Clase que representa una tarjeta de crédito
 */
public class Card {
    private Long cardNumber; // Número de la tarjeta
    private String email; // Email del dueño
    private String cardHolderName; // Nombre del dueño
    private int ccv; // Código ccv de la tarjeta
    private Date date; // Fecha de caducidad de la tarjeta

    /**
     * Constructor
     * 
     * @param cardNumber Número de la tarjeta
     * @param email Email del dueño
     * @param getCardHolderName Nombre del dueño
     * @param ccv Código ccv de la tarjeta
     * @param date Fecha de caducidad de la tarjeta
     */
    public Card(Long cardNumber, String email, String getCardHolderName, int ccv, Date date) {
        this.cardNumber = cardNumber;
        this.email = email;
        this.cardHolderName = getCardHolderName;
        this.ccv = ccv;
        this.date = date;
    }

    /**
     * Devuelve el número de la tarjeta
     * 
     * @return Número de la tarjeta
     */
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     * Establece el número de la tarjeta
     * 
     * @param cardNumber Número de la tarjeta
     */
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Devuelve el email del dueño
     * 
     * @return Email del dueño
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del dueño
     * 
     * @param email Email del dueño
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el nombre del dueño
     * 
     * @return Nombre del dueño
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * Establece el nombre del dueño
     * 
     * @param cardHolderName Nombre del dueño
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     * Devuelve el código ccv de la tarjeta
     * 
     * @return Código ccv de la tarjeta
     */
    public int getCcv() {
        return ccv;
    }

    /**
     * Establece el código ccv de la tarjeta
     * 
     * @param ccv Código ccv de la tarjeta
     */
    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    /**
     * Devuelve la fecha de caducidad de la tarjeta
     * 
     * @return Fecha de caducidad de la tarjeta
     */
    public Date getDate() {
        return date;
    }

    /**
     * Establece la fecha de caducidad de la tarjeta
     * 
     * @param date Fecha de caducidad de la tarjeta
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
