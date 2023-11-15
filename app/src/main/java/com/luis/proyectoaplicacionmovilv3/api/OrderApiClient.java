package com.luis.proyectoaplicacionmovilv3.api;

public class OrderApiClient {
    private static final OrderApiClient ourInstance = new OrderApiClient();

    private OrderApiClient() {
        // Constructor privado para evitar la creación de instancias fuera de la clase
    }

    public static OrderApiClient getInstance() {
        return ourInstance;
    }

    // Otros métodos y lógica de la clase
}
