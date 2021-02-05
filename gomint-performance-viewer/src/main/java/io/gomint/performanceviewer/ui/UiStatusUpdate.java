package io.gomint.performanceviewer.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 * @author Shad0wCore
 * @version 1.2
 */
public class UiStatusUpdate extends Label {

    private Paint borderColor = Paint.valueOf( "#191919" );
    private Paint backgroundColor = ColorPalette.UI_BACKGROUND_SECONDARY.getPaint();
    private Paint foregroundColor = ColorPalette.UI_PRIMARY.getPaint();

    public UiStatusUpdate() {
        this.update();
    }

    public UiStatusUpdate( String status ) {
        this.setText( status );
        this.update();
    }

    public UiStatusUpdate( ImageView image, String status ) {
        this.setText( status );
        this.setGraphic( image );
        this.update();
    }

    public Paint getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor( Paint borderColor ) {
        this.borderColor = borderColor;
        this.update();
    }

    public Paint getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor( Paint backgroundColor ) {
        this.backgroundColor = backgroundColor;
        this.update();
    }

    public Paint getForegroundColor() {
        return this.foregroundColor;
    }

    public void setForegroundColor( Paint foregroundColor ) {
        this.foregroundColor = foregroundColor;
        this.update();
    }

    private void update() {
        this.setBorder( new Border( new BorderStroke( this.borderColor, BorderStrokeStyle.SOLID,
                new CornerRadii( 30 ), new BorderWidths( 1.25 ), null ) ) );
        this.setBackground( new Background( new BackgroundFill( this.backgroundColor, new CornerRadii( 30 ), null ) ) );
        this.setPadding( new Insets( 10, 15, 10, 15 ) );
        this.setTextFill( this.foregroundColor );
    }

}
