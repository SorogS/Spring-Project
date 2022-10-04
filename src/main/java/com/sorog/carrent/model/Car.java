package com.sorog.carrent.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cars")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long car_id;

    @Column
    private String carName;

    @Column
    private BigDecimal costPerDay;

    @Column
    @Enumerated
    private CarTypes type;
    //TODO:image


    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] carImage;
}
