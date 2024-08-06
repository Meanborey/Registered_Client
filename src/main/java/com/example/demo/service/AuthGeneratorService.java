package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.User;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthGeneratorService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;

    public Client authenticateAndGenerateClient(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username);
        System.out.println("User: "+username+" Password: "+password);
        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new Exception("Invalid username or password");
        }

        // Check if client already exists for the user
        Client existingClient = clientRepository.findByClientId(user.getId());
        if (existingClient != null) {
            return existingClient;
        }

        // Generate client ID and client secret
        String clientId = UUID.randomUUID().toString();
        String clientSecret = UUID.randomUUID().toString();

        // Save client to the database
        Client client = new Client();
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);
        client.setUserId(user.getId());
        clientRepository.save(client);

        return client;
    }


}
