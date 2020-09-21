package com.app.converter.model;

import com.app.converter.model.enums.EngineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Engine {
    private EngineType engineType;
    private double power;
}
