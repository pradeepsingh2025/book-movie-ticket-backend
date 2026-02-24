package me.riazulislam.infinitecineplexbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseModel{
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = true)
    private String name;

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'USER'")
    private RoleEnum role;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Reservation> movieReservations = new ArrayList<>();
}
