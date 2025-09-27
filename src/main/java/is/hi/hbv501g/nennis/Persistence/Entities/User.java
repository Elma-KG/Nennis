package is.hi.hbv501g.nennis.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ourusers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;
    private String password;
    private String roles;
}