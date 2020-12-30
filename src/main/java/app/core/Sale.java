package app.core;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int costumeId;

    private String channel;
}
