package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setUnit(source.getUnit());
        return uom;
    }
}
