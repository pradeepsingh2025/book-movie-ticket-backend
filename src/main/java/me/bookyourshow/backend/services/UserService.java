package me.bookyourshow.backend.services;

import me.bookyourshow.backend.dtos.CreateUserDTO;
import me.bookyourshow.backend.dtos.UpdateUserDTO;
import me.bookyourshow.backend.dtos.UpdatePasswordDTO;
import me.bookyourshow.backend.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createNewUser(CreateUserDTO user);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO);

    void deleteUser(Long id);

    void updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO);
}
