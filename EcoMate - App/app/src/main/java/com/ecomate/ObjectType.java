package com.ecomate;

public enum ObjectType {
    AMARILLO,
    AZUL,
    VERDE,
    MEDICAMENTO,
    PILAS,
    RESTA,
    ROPA,
    RAEE,
    PUNTO,
    MARRON;

    public static String getByIndex(int index) {
        return ObjectType.values()[index].toString();
    }
    public static int getIndex(String value) {
        //if (value.toUpperCase() == "MEDICAMENTO") value = "MEDICAMENTOS";
        return ObjectType.valueOf(value.toUpperCase()).ordinal();
    }
}
