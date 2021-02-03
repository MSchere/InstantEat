package backend;

import java.io.Serializable;

/**
 * Clase que representa a un usuario de la aplicación.
 */
public class User implements Clonable, Serializable {
    private String email; // Email del usuario
    private String password; // Contraseña del usuario
    private String userType; // Tipo de usuario
    private String name; // Nombre del usuario
    private String address; // Dirección del usuario
    private int phoneNumber; // Número de teléfono del usuario

    /**
     * Constructor.
     *
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @param userType Tipo de usuario
     * @param name Nombre del usuario
     * @param address Dirección del usuario
     * @param phoneNumber Número de teléfono del usuario
     */
    public User(String email, String password, String userType, String name, String address, int phoneNumber) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Devuelve el email del usuario
     * 
     * @return Email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario
     * 
     * @param email Email del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la contraseña del usuario
     * 
     * @return Contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario
     * 
     * @param password Contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Devuelve el tipo de usuario
     * 
     * @return Tipo de usuario
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Establece el tipo de usuario
     * 
     * @param userType Tipo de usuario
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Devuelve el nombre del usuario
     * 
     * @return Nombre del usuario
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del usuario
     * 
     * @param name Nombre del usuario
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la dirección del usuario
     * 
     * @return Dirección del usuario
     */
    public String getAddress() {
        return address;
    }

    /**
     * Establece la dirección del usuario
     * 
     * @param address Dirección del usuario
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Devuelve el número de teléfono del usuario
     * 
     * @return Número de teléfono del usuario
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Establece el número de teléfono del usuario
     * 
     * @param phoneNumber Número de teléfono del usuario
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Devuelve una instancia de esta clase con los mismos datos que el usuario que llama a este método
     * 
     * @return Instancia de esta clase con los mismos datos que el usuario que lo llama a este método
     */
    @Override
    public Object copy() {
        return new User(this.email, this.password, this.userType, this.name, this.address, this.phoneNumber);
    }
}
