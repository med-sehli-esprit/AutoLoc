// EquipementServiceImpl.java
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement equipement) {
        equipement.setIdEquipement(null);
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        findById(id);
        equipement.setIdEquipement(id);
        return equipementRepository.save(equipement);
    }

    @Override
    public void delete(Long id) {
        equipementRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }
}