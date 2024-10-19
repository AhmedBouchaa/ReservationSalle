package com.mdw3.appgestionprojets.reservationsalle.repositories;

import com.mdw3.appgestionprojets.reservationsalle.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findById(Long id);
    @Query("from Utilisateur u where upper(u.nom) like upper(concat('%',:keyword,'%') ) or upper(u.prenom) like upper(concat('%',:keyword,'%') )")
    List<Utilisateur> findByNomContainingIgnoreCase(String keyword);

}
