package com.luis.proyectoaplicacionmovilv3.models;

import androidx.annotation.NonNull;



public class Pedido {
    @NonNull

    public String nroPedido;

    public String eventPedido;

    public String empresaPedido;

    public Pedido(@NonNull String nroPedido, String eventPedido, String empresaPedido) {
        this.nroPedido = nroPedido;
        this.eventPedido = eventPedido;
        this.empresaPedido = empresaPedido;
    }
}
