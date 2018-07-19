package io.gomint.performanceviewer.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TPSChart {

    private final LineChart<Number, Number> chart;
    private final List<Long> data = new ArrayList<>();
    private final XYChart.Series<Number, Number> fullTimeSeries;
    private final XYChart.Series<Number, Number> actualTimeSeries;
    private final XYChart.Series<Number, Number> averageTimeSeries;
    private final ScrollBar scrollBar;
    private int currentDataStart;
    private final NumberAxis xAxis;

    private long tickNanos = 7812500;

    public TPSChart() {
        final NumberAxis yAxis = new NumberAxis();

        this.xAxis = new NumberAxis( 0, 512, 1000 );
        this.xAxis.setAutoRanging( false );

        this.chart = new LineChart<>( xAxis, yAxis );
        this.chart.setAnimated( false );
        this.chart.setCreateSymbols( false );
        this.chart.setLegendVisible( false );

        this.fullTimeSeries = new XYChart.Series<>();
        this.actualTimeSeries = new XYChart.Series<>();
        this.averageTimeSeries = new XYChart.Series<>();

        this.scrollBar = new ScrollBar();
        this.scrollBar.valueProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) {
                currentDataStart = (int) ( newValue.floatValue() * ( TimeUnit.SECONDS.toNanos( 1 ) / tickNanos ) * 60 );
                updateChart();
            }
        } );
    }

    public void addData( List<Long> time ) {
        this.data.addAll( time );
    }

    public Node getNode() {
        this.chart.getData().addAll( this.actualTimeSeries, this.fullTimeSeries, this.averageTimeSeries );
        this.chart.setPrefHeight( 200 );

        this.fullTimeSeries.getNode().setStyle( "-fx-stroke-width: 2px; -fx-stroke: #f34602;" );
        this.actualTimeSeries.getNode().setStyle( "-fx-stroke-width: 2px; -fx-stroke: #f39502;" );
        this.averageTimeSeries.getNode().setStyle( "-fx-stroke-width: 2px; -fx-stroke: #45df02;" );

        updateChart();

        return this.chart;
    }

    private void updateChart() {
        this.fullTimeSeries.getData().clear();
        this.actualTimeSeries.getData().clear();
        this.averageTimeSeries.getData().clear();

        this.xAxis.setLowerBound( this.currentDataStart );
        this.xAxis.setUpperBound( this.currentDataStart + 60 * ( TimeUnit.SECONDS.toNanos( 1 ) / this.tickNanos ) );

        List<XYChart.Data<Number, Number>> fullTimeData = new ArrayList<>();
        List<XYChart.Data<Number, Number>> actualTimeData = new ArrayList<>();
        List<XYChart.Data<Number, Number>> averageTimeData = new ArrayList<>();

        int tps = (int) (TimeUnit.SECONDS.toNanos( 1 ) / this.tickNanos);
        for ( int i = (int) this.xAxis.getLowerBound(); i < this.xAxis.getUpperBound(); i++ ) {
            if ( this.data.size() > i ) {
                fullTimeData.add( new XYChart.Data<>( i, this.tickNanos ) );
                actualTimeData.add( new XYChart.Data<>( i, this.data.get( i ) ) );
            }

            if ( i > 0 && i % tps == 0 ) {
                // Calc the average
                double average = 0;

                for ( int x = i - tps; x < i; x++ ) {
                    average += this.data.get( x );
                }

                average = average / tps;

                for ( int x = i - tps; x < i; x++ ) {
                    averageTimeData.add( new XYChart.Data<>( x, average ) );
                }
            }
        }

        // Calc the average
        double average = 0;

        for ( int x = (int) (this.xAxis.getUpperBound() - tps); x < this.xAxis.getUpperBound(); x++ ) {
            average += this.data.get( x );
        }

        average = average / tps;

        for ( int x = (int) (this.xAxis.getUpperBound() - tps); x < this.xAxis.getUpperBound(); x++ ) {
            averageTimeData.add( new XYChart.Data<>( x, average ) );
        }

        this.fullTimeSeries.getData().addAll( fullTimeData );
        this.actualTimeSeries.getData().addAll( actualTimeData );
        this.averageTimeSeries.getData().addAll( averageTimeData );
    }

    public Node getScroller() {
        VBox.setMargin( this.scrollBar, new Insets( 0, 20, 0, 20 ) );
        this.scrollBar.setMax( ( ( this.data.size() / ( TimeUnit.SECONDS.toNanos( 1 ) / this.tickNanos ) ) / 60 ) - 1 );
        return this.scrollBar;
    }

}
