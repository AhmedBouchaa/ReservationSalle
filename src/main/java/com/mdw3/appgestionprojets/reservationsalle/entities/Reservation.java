package com.mdw3.appgestionprojets.reservationsalle.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;


import java.util.Date;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter @Setter private LocalDateTime date;
    @Getter @Setter private String description;
    @Getter @Setter private int cout;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @Getter @Setter @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle;

    @OneToOne (mappedBy="reservation")
    private Evenement evenement;
    @OneToOne (mappedBy="reservation")
    private Payement payement;
}
