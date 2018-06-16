package io.gomint.performanceviewer.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * @author Shad0wCore
 * @version 1.2
 */
public class UiNoReportOpen extends StackPane {

    private final Image goMintLogo = new Image( getClass().getResourceAsStream( "/gomint.png" ) );
    private final ImageView goMintLogoView = new ImageView( goMintLogo );
    private final Label performanceViewerTitle = new Label( "Performance Viewer" );
    private final Label performanceViewerSubTitle = new Label( "Neat tool to show you what is going on,\n" +
            "on your GoMint server" );

    public UiNoReportOpen() {
        this.setBackground( new Background( new BackgroundFill( ColorPalette.UI_BACKGROUND.getPaint(),
                null, null ) ) );

        this.goMintLogoView.setFitWidth( 150 );
        this.goMintLogoView.setFitHeight( 150 );

        this.performanceViewerTitle.setFont( Font.font( 20 ) );
        this.performanceViewerTitle.setTextFill( ColorPalette.UI_FOREGROUND.getPaint() );

        this.performanceViewerSubTitle.setTextFill( ColorPalette.UI_SECONDARY_FOREGROUND.getPaint() );

        VBox mainContainer = new VBox();
        HBox secondaryContainer = new HBox();
        VBox textContainer = new VBox();

        Pane separator = new Pane();
        separator.setPrefHeight( 2 );
        separator.setMaxWidth( 500 );
        separator.setBackground( new Background( new BackgroundFill( ColorPalette.UI_BACKGROUND_SECONDARY.getPaint(),
                null, null ) ) );

        mainContainer.setSpacing( 20 );
        mainContainer.setAlignment( Pos.CENTER );
        mainContainer.getChildren().addAll( new UiStatusUpdate( "Current build status | Bloody Edge" ),
                secondaryContainer, separator, new UiButton( "Open performance report" ) );

        secondaryContainer.setSpacing( 20 );
        secondaryContainer.setAlignment( Pos.CENTER );
        secondaryContainer.getChildren().addAll( this.goMintLogoView, textContainer );

        textContainer.setSpacing( 5 );
        textContainer.setAlignment( Pos.CENTER_LEFT );
        textContainer.getChildren().addAll( this.performanceViewerTitle, this.performanceViewerSubTitle );

        this.setAlignment( Pos.CENTER );

        this.getChildren().addAll( mainContainer );
    }


}
