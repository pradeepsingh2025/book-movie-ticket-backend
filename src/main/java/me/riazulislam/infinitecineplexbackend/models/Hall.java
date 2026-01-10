package me.riazulislam.infinitecineplexbackend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import me.riazulislam.infinitecineplexbackend.enums.HallType;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

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
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "availability_status", columnDefinition = "jsonb")
    private Map<Long, String> availabilityStatus = new HashMap<>();
    
    // Note: availabilityStatus format:
    // Key: showId (Long)
    // Value: "YYYY-MM-DD HH:mm:ss - HH:mm:ss" (e.g., "2026-01-15 19:15:00 - 22:30:00")
}