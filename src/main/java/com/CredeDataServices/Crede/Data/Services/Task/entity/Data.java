package com.CredeDataServices.Crede.Data.Services.Task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tenderRegistrationNumber;
    private String qualityTypeAndQuantity;
    private String placeOfWork;
    private String tenderType;

}