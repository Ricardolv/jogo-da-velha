package com.richard.api.resources.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private PositionDTO position;
    private String firstPlayer;
    private String player;

    private String status;
    private String winner;
    private String msg;

}
