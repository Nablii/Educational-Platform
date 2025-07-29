package tn.enis.service;


import org.springframework.stereotype.Service;
import tn.enis.models.User;
import tn.enis.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {

        return userRepository.findById(id);
    }

    public Optional<User> login(String fullName, String password) {

        return userRepository.findByFullNameAndPassword(fullName, password);
    }

    public boolean userExists(String fullName, String email) {
        return userRepository.findByFullNameOrEmail(fullName, email).isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
