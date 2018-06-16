package io.gomint.performanceviewer.ui;

import javafx.scene.paint.Paint;

/**
 * @author Shad0wCore
 * @version 1.2
 */
public enum ColorPalette {

    UI_BACKGROUND("#202020"),
    UI_BACKGROUND_SECONDARY("#303030"),
    UI_FOREGROUND("#FFFFFF"),
    UI_SECONDARY_FOREGROUND("#777"),
    UI_PRIMARY("#2cc97a");

    private final String hexValue;

    ColorPalette( String hexValue ) {
        this.hexValue = hexValue;
    }

    public String getHexValue() {
        return hexValue;
    }

    public Paint getPaint() {
        return Paint.valueOf( getHexValue() );
    }

    @Override
    public String toString() {
        return getHexValue();
    }

}
