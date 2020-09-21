package com.app.converter.model;

import com.app.converter.model.enums.TyreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Wheel {
    private String model;
    private int size;
    private TyreType tyreType;
}
