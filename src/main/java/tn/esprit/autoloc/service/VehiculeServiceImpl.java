package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Vehicule create(Vehicule vehicule) {
        if (vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new DuplicateResourceException(
                    "Immatriculation deja utilisee : " + vehicule.getImmatriculation());
        }
        vehicule.setIdVehicule(null);
        if (vehicule.getStatut() == null) {
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule update(Long id, Vehicule data) {
        Vehicule existing = findById(id);
        boolean immatriculationChangee =
                !existing.getImmatriculation().equals(data.getImmatriculation());
        if (immatriculationChangee
                && vehiculeRepository.existsByImmatriculation(data.getImmatriculation())) {
            throw new DuplicateResourceException(
                    "Immatriculation deja utilisee : " + data.getImmatriculation());
        }
        existing.setImmatriculation(data.getImmatriculation());
        existing.setMarque(data.getMarque());
        existing.setModele(data.getModele());
        existing.setCategorie(data.getCategorie());
        existing.setTarifJournalier(data.getTarifJournalier());
        existing.setStatut(data.getStatut());
        return vehiculeRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        vehiculeRepository.delete(findById(id));
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule introuvable : id=" + id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public List<Vehicule> findByStatut(StatutVehicule statut) {
        return vehiculeRepository.findByStatut(statut);
    }

    @Override
    public List<Vehicule> findByAgence(Long idAgence) {
        return vehiculeRepository.findByAgence_IdAgence(idAgence);
    }
}