package com.example.instanteat;

import java.util.Date;

public class Card {
    private Long cardNumber;
    private String email;
    private String cardHolderName;
    private int ccv;
    private Date date;

    public Card(Long cardNumber, String email, String getCardHolderName, int ccv, Date date) {
        this.cardNumber = cardNumber;
        this.email = email;
        this.cardHolderName = getCardHolderName;
        this.ccv = ccv;
        this.date = date;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
