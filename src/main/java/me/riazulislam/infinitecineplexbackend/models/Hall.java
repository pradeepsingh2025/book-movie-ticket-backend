package me.riazulislam.infinitecineplexbackend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
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
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "availability_status", columnDefinition = "jsonb")
    private Map<Long, String> availabilityStatus = new HashMap<>();
}