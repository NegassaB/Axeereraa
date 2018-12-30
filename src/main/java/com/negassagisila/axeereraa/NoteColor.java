package com.negassagisila.axeereraa;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public enum NoteColor {
    lightGreen(102, 255, 102), lightYellow(255, 229, 204), lightRed(255, 102, 102);

    private final int redValue;
    private final int greenValue;
    private final int blueValue;

    NoteColor(int redValue, int greenValue, int blueValue) {

        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    static Color getTheColorOfTheNote(@NotNull NoteColor color) {
        return new Color(color.redValue, color.greenValue, color.blueValue);
    }

    static Color getTheColorOfTheNote() {
        return new Color(
                lightYellow.redValue,
                lightYellow.greenValue,
                lightYellow.blueValue
        );
    }


}
