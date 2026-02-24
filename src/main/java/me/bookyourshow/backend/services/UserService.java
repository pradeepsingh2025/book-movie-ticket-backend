package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.CreateUserDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateUserDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdatePasswordDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createNewUser(CreateUserDTO user);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO);

    void deleteUser(Long id);

    void updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO);
}
