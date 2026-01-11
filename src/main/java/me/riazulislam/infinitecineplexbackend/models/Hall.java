package me.riazulislam.infinitecineplexbackend.models;

import jakarta.persistence.*;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.HallType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "halls")
public class Hall extends BaseModel {
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HallType type;

}