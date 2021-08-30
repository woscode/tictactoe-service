package com.wos.tictactoeservice.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
 
@Converter(autoApply = true)
public class MarkConverter implements AttributeConverter<Mark, String> {
 
    @Override
    public String convertToDatabaseColumn(Mark attribute) {
        switch (attribute) {
            case E:
                return " ";
            case X:
                return "x";
            case O:
                return "o";
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }
 
    @Override
    public Mark convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case " ":
                return Mark.E;
            case "x":
                return Mark.X;
            case "o":
                return Mark.O;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}

// @Convert(converter = MarkConverter.class)