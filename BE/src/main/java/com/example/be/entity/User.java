package com.example.be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.role;
@Builder
@Getter
@Setter
@Entity
@Table(name = "\"User\"")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 10)
    @NotNull
    @Nationalized
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @NotNull
    @ColumnDefault("getdate()")
    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @NotNull
    @ColumnDefault("getdate()")
    @Column(name = "modifiedDate", nullable = false)
    private Instant modifiedDate;

    @Nationalized
    @Lob
    @Column(name = "fullName")
    private String fullName;

    @Nationalized
    @Lob
    @Column(name = "address")
    private String address;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "isActive", nullable = false)
    private Boolean isActive = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "isDelete", nullable = false)
    private Boolean isDelete = false;

    @Nationalized
    @Lob
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 6)
    @Column(name = "OTP", length = 6)
    private String otp;

    @Column(name = "expireDate")
    private Instant expireDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roleID", nullable = false)
    private Role roleID;

    @OneToMany(mappedBy = "customer")
    private Set<Complaint> complaints = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userID")
    private Set<Lessor> lessors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customerID")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userID")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<TournamentRegistration> tournamentRegistrations = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // email in our case
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}