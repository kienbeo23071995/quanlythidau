package com.example.be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "startDate", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "endDate", nullable = false)
    private Instant endDate;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @ColumnDefault("getdate()")
    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @NotNull
    @ColumnDefault("getdate()")
    @Column(name = "modifiedDate", nullable = false)
    private Instant modifiedDate;

    @OneToMany(mappedBy = "tournament")
    private Set<TournamentRegistration> tournamentRegistrations = new LinkedHashSet<>();

}