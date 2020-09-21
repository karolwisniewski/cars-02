package com.app.converter.json;

import com.app.converter.json.generic.JsonConverter;
import com.app.converter.model.Car;

import java.util.List;

public class CarsJsonConverter extends JsonConverter<List<Car>> {
    public CarsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
