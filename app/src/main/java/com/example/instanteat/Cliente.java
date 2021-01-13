package com.example.instanteat;

public class Cliente extends Usuario{
    
    private String apellidos;
    private String direccion;
    private String telefono;
    private int tarjeta;
        
    public Cliente(String nombre, String apellidos, String direccion, String email, String telefono, int tarjeta){
        super(nombre,email);
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tarjeta = tarjeta;
    }
    
}
