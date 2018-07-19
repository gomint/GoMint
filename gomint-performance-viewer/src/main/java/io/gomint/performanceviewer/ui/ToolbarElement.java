package io.gomint.performanceviewer.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ToolbarElement {

    private final ToolBar toolBar;
    private Label reportName;

    public ToolbarElement() {
        this.toolBar = new ToolBar(
                new Button( "Open" ),
                new Separator(  ),
                new Label( "Currently open performance report: " ),
                this.reportName = new Label( "None" )
        );
    }

    public Node getNode() {
        return this.toolBar;
    }

}
