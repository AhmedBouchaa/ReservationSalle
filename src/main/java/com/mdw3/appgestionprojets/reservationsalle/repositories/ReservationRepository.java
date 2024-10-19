package com.mdw3.appgestionprojets.reservationsalle.repositories;

import com.mdw3.appgestionprojets.reservationsalle.entities.Reservation;
import com.mdw3.appgestionprojets.reservationsalle.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUtilisateur(Utilisateur utilisateur);
    Optional<Reservation> findById(Long id);
}
