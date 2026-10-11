package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);

    Vehicule update(Long id, Vehicule vehicule);

    void delete(Long id);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    List<Vehicule> findByStatut(StatutVehicule statut);

    List<Vehicule> findByAgence(Long idAgence);
}