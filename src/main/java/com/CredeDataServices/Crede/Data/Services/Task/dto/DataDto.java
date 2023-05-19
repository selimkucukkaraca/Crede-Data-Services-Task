package com.CredeDataServices.Crede.Data.Services.Task.dto;

import lombok.Builder;

@Builder
public record DataDto(
        String tenderRegistrationNumber,
        String qualityTypeAndQuantity,
        String placeOfWork,
        String tenderType,
        String url
) {
}
