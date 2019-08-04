package com.richard.api.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinColumn(name="game_id")
    Set<Position> positions = new HashSet<>();

    private String nextPlayer;

    public boolean isNinePlays() {
        return 9 == this.positions.size();
    }
}
