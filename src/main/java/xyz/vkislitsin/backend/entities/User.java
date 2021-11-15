package xyz.vkislitsin.backend.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private BigDecimal balance;
}
