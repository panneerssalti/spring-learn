package com.panneer.springlearn.batch.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COFFEE_INFO")
public class Coffee {
    @Id
    @Column(name = "CUSTOMER_ID")
    private int id;
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "ORIGIN")
    private String origin;
    @Column(name = "CHARACTERISTICS")
    private String characteristics;

    @Transient
    @Column(name = "TIME_STAMP")
    private String timeStamp;
}
