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
public class PositionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
}
