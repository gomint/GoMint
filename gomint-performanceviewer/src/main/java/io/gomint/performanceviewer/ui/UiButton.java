package io.gomint.performanceviewer.ui;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 * @author Shad0wCore
 * @version 1.2
 */
public class UiButton extends Button {

    private final Background idleStateBackground = new Background( new BackgroundFill(
            ColorPalette.UI_BACKGROUND_SECONDARY.getPaint(), new CornerRadii( 20 ),
            new Insets( 0, -10, 0, -10 ) ) );

    private final Background hoverStateBackground = new Background( new BackgroundFill(
            ColorPalette.UI_PRIMARY.getPaint(), new CornerRadii( 20 ),
            new Insets( 0, -15, 0, -15 ) ) );

    public UiButton() {
        this.setupStyle();
    }

    public UiButton( String text ) {
        super( text );
        this.setupStyle();
    }

    public UiButton( String text, Node graphic ) {
        super( text, graphic );
        this.setupStyle();
    }

    protected void setupStyle() {
        this.setTextFill( ColorPalette.UI_FOREGROUND.getPaint() );
        this.setBackground( this.idleStateBackground );

        this.hoverProperty().addListener( ( observable, oldValue, newValue ) -> {
            // Hovered
            if ( newValue ) {
                this.setBackground( this.hoverStateBackground );
                this.setCursor( Cursor.HAND );
                return;
            }

            // Not hovered
            this.setBackground( this.idleStateBackground );
            this.setCursor( Cursor.DEFAULT );
        } );
    }

}
