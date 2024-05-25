package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Lessors")
public class Lessor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userID", nullable = false)
    private User userID;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "business_name", nullable = false)
    private String businessName;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "business_address", nullable = false)
    private String businessAddress;

    @OneToMany(mappedBy = "lessorID")
    private Set<BadmintonCourt> badmintonCourts = new LinkedHashSet<>();

}