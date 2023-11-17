package com.luis.proyectoaplicacionmovilv3.dtos;
import java.util.UUID;

public class CreateOrderEventDto {
    private String observations;
    private String mainImageURL;
    private String referenceImageURL;
    private String longitude;
    private String latitude;
    private int eventId;
    private UUID userId;
    private UUID orderId;
}
