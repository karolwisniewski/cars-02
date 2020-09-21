package com.app.converter.model;

import com.app.converter.model.enums.CarBodyColor;
import com.app.converter.model.enums.CarBodyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CarBody {
    private CarBodyColor color;
    private CarBodyType bodyType;
    private List<String> components;
}
