// MaintenanceServiceImpl.java
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        maintenance.setIdMaintenance(null);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        findById(id);
        maintenance.setIdMaintenance(id);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public void delete(Long id) {
        maintenanceRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }
}