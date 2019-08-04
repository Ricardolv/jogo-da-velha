package com.richard.api.repositories;

import com.richard.api.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {

}
