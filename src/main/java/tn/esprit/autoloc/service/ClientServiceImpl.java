package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    @Transactional
    public Client create(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new DuplicateResourceException("Email deja utilise : " + client.getEmail());
        }
        if (clientRepository.existsByNumPermis(client.getNumPermis())) {
            throw new DuplicateResourceException(
                    "Numero de permis deja utilise : " + client.getNumPermis());
        }
        client.setIdClient(null);
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client update(Long id, Client data) {
        Client existing = findById(id);
        if (!existing.getEmail().equals(data.getEmail())
                && clientRepository.existsByEmail(data.getEmail())) {
            throw new DuplicateResourceException("Email deja utilise : " + data.getEmail());
        }
        if (!existing.getNumPermis().equals(data.getNumPermis())
                && clientRepository.existsByNumPermis(data.getNumPermis())) {
            throw new DuplicateResourceException(
                    "Numero de permis deja utilise : " + data.getNumPermis());
        }
        existing.setNom(data.getNom());
        existing.setPrenom(data.getPrenom());
        existing.setEmail(data.getEmail());
        existing.setTelephone(data.getTelephone());
        existing.setNumPermis(data.getNumPermis());
        return clientRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clientRepository.delete(findById(id));
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : id=" + id));
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : email=" + email));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}