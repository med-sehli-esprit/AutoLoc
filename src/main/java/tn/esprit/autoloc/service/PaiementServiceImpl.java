// PaiementServiceImpl.java
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement create(Paiement paiement) {
        paiement.setIdPaiement(null);
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Long id, Paiement paiement) {
        findById(id);
        paiement.setIdPaiement(id);
        return paiementRepository.save(paiement);
    }

    @Override
    public void delete(Long id) {
        paiementRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
}