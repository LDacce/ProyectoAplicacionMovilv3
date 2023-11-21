package com.luis.proyectoaplicacionmovilv3.dtos;

public class UpdateOrderEventDto extends CreateOrderEventDto{
    public UpdateOrderEventDto(int eventId, String userId, String orderId, String observations, String mainImageURL, String referenceImageURL, String longitude, String latitude) {
        super(eventId, userId, orderId, observations, mainImageURL, referenceImageURL, longitude, latitude);
    }
}
