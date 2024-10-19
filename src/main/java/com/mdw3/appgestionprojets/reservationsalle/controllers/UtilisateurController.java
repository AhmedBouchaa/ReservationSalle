package com.mdw3.appgestionprojets.reservationsalle.controllers;

import com.mdw3.appgestionprojets.reservationsalle.entities.Reservation;
import com.mdw3.appgestionprojets.reservationsalle.entities.Utilisateur;
import com.mdw3.appgestionprojets.reservationsalle.repositories.ReservationRepository;
import com.mdw3.appgestionprojets.reservationsalle.repositories.UtilisateurRepository;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        if(utilisateur == null){
            throw new RuntimeException("l'utilisateur ne peut pas etre null");
        }
        if(utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty() || utilisateur.getPassword() == null || utilisateur.getPassword().trim().isEmpty() || utilisateur.getPassword() == null){
            throw new RuntimeException("le mail et le mot de passe ne peut pas etre null");
        }
        Optional<Utilisateur> existingUtilisateur =utilisateurRepository.findByEmail(utilisateur.getEmail());
        if(existingUtilisateur.isPresent()){
          throw new RuntimeException("Cet email existe deja ");
        }
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);

        return savedUtilisateur;
    }
    @GetMapping
    public List<Utilisateur> getAllUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        if(utilisateurs.isEmpty()){
            throw new RuntimeException("Aucun utilisateur trouvé");
        }
        return utilisateurs;
    }
    @GetMapping("/{id}")
    public Utilisateur getUtilisateurById(@PathVariable long id) {
        Optional<Utilisateur> utilisateur=utilisateurRepository.findById(id);
        if(utilisateur.isEmpty()){
            throw new RuntimeException("Utilisateur non trouvé");
        }
        return utilisateur.get();
    }
    @DeleteMapping("/{id}")
    public String deleteUtilisateur(@PathVariable long id) {
        Optional<Utilisateur> utilisateur=utilisateurRepository.findById(id);
        if(utilisateur.isEmpty()){
            throw new RuntimeException("Utilisateur non trouvé");
        }
        if(utilisateur.get().getReservations().isEmpty()){
            utilisateurRepository.deleteById(id);
            return "Utilisateur supprimee avec succèes";
        }
        else{
            throw new RuntimeException("Impossible de supprimer le utilisateur car il a effectees des reservations");
        }
    }
    @PutMapping
    public Utilisateur updateUtilisateur(@RequestBody Utilisateur uputilisateur)
    {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(uputilisateur.getId())
                .orElseThrow(()-> new RuntimeException("Utilisateur non trouve"));
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(uputilisateur.getEmail());
        if(utilisateur.isPresent() && (!utilisateur.get().getEmail().equals(uputilisateur.getEmail()))){
            throw new RuntimeException("Un Utilisateur avec ce mail existe deja");
        }
        existingUtilisateur.setNom(uputilisateur.getNom());
        existingUtilisateur.setPrenom(uputilisateur.getPrenom());
        existingUtilisateur.setTelephone(uputilisateur.getTelephone());
        existingUtilisateur.setEmail(uputilisateur.getEmail());
        existingUtilisateur.setPassword(uputilisateur.getPassword());
        return utilisateurRepository.save(existingUtilisateur);
    }
    @GetMapping("/{id}/reservations")
    public List<Reservation> getReservationsByUtilisateurId(@PathVariable long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(()->new RuntimeException("Utilisateur non trouve"));
        List<Reservation> reservations = utilisateur.getReservations();
        if(reservations.isEmpty()){
            throw new RuntimeException("Aucune reservation assossie a cet utilisateur");
        }
        return reservations;
    }
    @GetMapping("/recherche")
    public List<Utilisateur> searchUtilisateur(@RequestParam String keyword) {
        List<Utilisateur> resultats=utilisateurRepository.findByNomContainingIgnoreCase(keyword);
        if(resultats.isEmpty()){
            throw new RuntimeException("Aucun utilisateur trouvé avec le mot-clé:" + keyword);
        }
        return resultats;
    }
    @PostMapping("add/{reservationId}/to/{userId}")
    public String addReservationToUtilisateur(@PathVariable long reservationId, @PathVariable long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElseThrow(()->new RuntimeException("Utilisateur non trouve"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("Utilisateur non trouve"));
        if(reservation.getUtilisateur() != null && reservation.getUtilisateur().getId() == userId){
            throw new RuntimeException("la reservation est deja affecter a cet utilisateur");
        }
        reservation.setUtilisateur(utilisateur);
        utilisateur.getReservations().add(reservation);
        utilisateurRepository.save(utilisateur);
        return "Reservation affecte";
    }
    @DeleteMapping("/remove/{reservationId}/to/{userId}")
    public String removeReservationFromUtilisateur(@PathVariable long reservationId, @PathVariable long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElseThrow(()->new RuntimeException("Utilisateur non trouve"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("Utilisateur non trouve"));

        if(!utilisateur.getReservations().contains(reservation)){
            throw new RuntimeException("la reservation n'est pas affecter a cet utilisateur");
        }
        utilisateur.getReservations().remove(reservation);
        reservation.setUtilisateur(null);
        utilisateurRepository.save(utilisateur);
        return "Reservation retire de l'utilisateur avec succes";
    }
}
