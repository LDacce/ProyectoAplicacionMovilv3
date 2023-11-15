package com.luis.proyectoaplicacionmovilv3.api;

public class OrderEventApiClient {
    private static final OrderEventApiClient ourInstance = new OrderEventApiClient();

    private OrderEventApiClient() {
        // Constructor privado para evitar la creación de instancias fuera de la clase
    }

    public static OrderEventApiClient getInstance() {
        return ourInstance;
    }

    // Otros métodos y lógica de la clase
}




