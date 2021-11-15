package xyz.vkislitsin.backend.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Loan")
public class Loan {
    @Id
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "borrower_id")
    private User borrower;

    @Column
    private BigDecimal amount;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "debtor_id")
    private User debtor;
}
