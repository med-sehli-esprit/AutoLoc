package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;

public interface IClientService {

    Client create(Client client);

    Client update(Long id, Client client);

    void delete(Long id);

    Client findById(Long id);

    Client findByEmail(String email);

    List<Client> findAll();
}