package io.gomint.performanceviewer;

import io.gomint.performanceviewer.ui.TPSChart;
import io.gomint.performanceviewer.ui.ToolbarElement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Viewer extends Application {

    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {
        Platform.setImplicitExit( false );

        ToolbarElement toolbarElement = new ToolbarElement();
        TPSChart chart = new TPSChart();

        VBox layout = new VBox();
        layout.setAlignment( Pos.TOP_LEFT );
        Label tpsChartLabel = new Label( "TPS Chart:" );
        // VBox.setMargin( tpsChartLabel, new Insets( 0, 10, 0, 0 ) );
        layout.getChildren().addAll( toolbarElement.getNode(), tpsChartLabel );

        new Thread( new Runnable() {
            BlockingQueue<Long> queue = new LinkedBlockingQueue<>();
            AtomicBoolean chartDone = new AtomicBoolean( false );
            AtomicInteger added = new AtomicInteger( 0 );

            AtomicBoolean attachedProgressbar = new AtomicBoolean( false );
            ProgressBar progressBar = new ProgressBar( 0 );

            @Override
            public void run() {
                Thread.currentThread().setName( "Chart calculator" );

                startFXChecking();

                for ( int i = 0; i < 3 * 60 * 60 * 128; i++ ) {
                    this.queue.add( ThreadLocalRandom.current().nextLong( (long) ( 7812500 * 1.2 ) ) );
                }

                this.chartDone.set( true );
            }

            private void startFXChecking() {
                Platform.runLater( new Runnable() {
                    @Override
                    public void run() {
                        if ( !attachedProgressbar.get() ) {
                            attachedProgressbar.set( true );

                            VBox.setMargin( progressBar, new Insets( 50, 0, 0, 200 ) );
                            layout.getChildren().add( progressBar );
                        }

                        progressBar.setPrefWidth( layout.getWidth() - 400 );

                        int renderedInThisRun = 0;
                        List<Long> toAdd = new ArrayList<>();

                        while ( queue.size() > 0 && renderedInThisRun++ < 500 ) {
                            toAdd.add( queue.poll() );
                        }

                        chart.addData( toAdd );

                        double needed = 3 * 60 * 512;
                        double progress = (double) added.addAndGet( renderedInThisRun ) / needed;
                        progressBar.setProgress( progress );

                        if ( !chartDone.get() || queue.size() > 0 ) {
                            startFXChecking();
                        } else {
                            layout.getChildren().remove( progressBar );
                            layout.getChildren().add( chart.getNode() );
                            layout.getChildren().add( chart.getScroller() );
                        }
                    }
                } );
            }
        } ).start();


        Scene stage = new Scene( layout, 300, 250 );

        layout.prefWidthProperty().bind( stage.widthProperty() );
        layout.prefHeightProperty().bind( stage.heightProperty() );

        primaryStage.getIcons().addAll( new Image( "/gomint.png" ) );
        primaryStage.setTitle( "GoMint Performance Viewer" );
        primaryStage.setMaximized( true );
        primaryStage.setScene( stage );
        primaryStage.show();
    }

}
