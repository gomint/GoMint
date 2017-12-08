package io.gomint.server.gui.element;

import org.json.simple.JSONObject;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Label extends Element {

    public Label( String id, String text ) {
        super( id, text );
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put( "type", "label" );
        return obj;
    }

}
