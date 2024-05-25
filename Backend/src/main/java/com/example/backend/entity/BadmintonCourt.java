package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "BadmintonCourts")
public class BadmintonCourt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lessorID", nullable = false)
    private Lessor lessorID;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "courtName", nullable = false)
    private String courtName;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "price_per_hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "isAvailable", nullable = false)
    private Boolean isAvailable = false;

    @OneToMany(mappedBy = "courtID")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "court")
    private Set<Review> reviews = new LinkedHashSet<>();

}