package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateUserDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateUserDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdatePasswordDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UserDTO;
import me.riazulislam.infinitecineplexbackend.enums.RoleEnum;
import me.riazulislam.infinitecineplexbackend.models.User;
import me.riazulislam.infinitecineplexbackend.repositories.UserRepository;
import me.riazulislam.infinitecineplexbackend.services.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDTO createNewUser(CreateUserDTO user) {
        // Normalize email early to avoid case or surrounding whitespace producing
        // duplicate entries (e.g., "User@Example.com" vs "user@example.com").
        String normalizedEmail = user.getEmail() == null ? null : user.getEmail().trim().toLowerCase();

        if (normalizedEmail == null || normalizedEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        // Basic uniqueness check to provide fast feedback; DB-level unique constraint
        // is the ultimate guard against races.
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        System.out.println("Before encoding the password in createNewUSer[UserServiceImpl.createNewUser]------------------------------- "+user.getPassword());

        String encoded = passwordEncoder.encode(user.getPassword());

        System.out.println("After encoding the password in createNewUSer[UserServiceImpl.createNewUser]------------------------------- "+encoded);

        User newUser = User.builder()
                .email(normalizedEmail)
                .password(encoded)
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(RoleEnum.USER)
                .build();

        User createdUser;
        try {
            createdUser = userRepository.save(newUser);
        } catch (DataIntegrityViolationException ex) {
            // Handle race where two requests pass the existsByEmail check simultaneously
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        UserDTO createdUserDTO = UserDTO.builder()
                .email(createdUser.getEmail())
                .name(createdUser.getName())
                .phoneNumber(createdUser.getPhoneNumber())
                .role(createdUser.getRole() == null ? null : createdUser.getRole().name())
                .build();

        System.out.println("Created user DTO from [UserServiceLmpl.createNewUser]-----------------"+ createdUserDTO);

        return createdUserDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        return convertToDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        // Update fields only if provided
        if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().isEmpty()) {
            String normalizedEmail = updateUserDTO.getEmail().trim().toLowerCase();

            // Check if email is being changed and if new email already exists
            if (!user.getEmail().equals(normalizedEmail) && userRepository.existsByEmail(normalizedEmail)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
            }
            user.setEmail(normalizedEmail);
        }

        if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
            String encoded = passwordEncoder.encode(updateUserDTO.getPassword());
            user.setPassword(encoded);
        }

        if (updateUserDTO.getName() != null) {
            user.setName(updateUserDTO.getName());
        }

        if (updateUserDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }

        try {
            User updatedUser = userRepository.save(user);
            return convertToDTO(updatedUser);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        // Verify old password matches
        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        // Update to new password
        String encodedNewPassword = passwordEncoder.encode(updatePasswordDTO.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole() == null ? null : user.getRole().name())
                .build();
    }
}
