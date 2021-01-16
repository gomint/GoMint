package io.gomint.server.gui;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FormResponse implements io.gomint.gui.FormResponse {

    private Map<String, Object> answers = new HashMap<>();

    public void addAnswer( String id, Object data ) {
        this.answers.put( id, data );
    }

    @Override
    public Boolean toggle(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof Boolean ) {
                return (Boolean) val;
            }
        }

        return null;
    }

    @Override
    public String stepSlider(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof String ) {
                return (String) val;
            }
        }

        return null;
    }

    @Override
    public Float slider(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof Double ) {
                return ( (Double) val ).floatValue();
            }
        }

        return null;
    }

    @Override
    public String input(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof String ) {
                return (String) val;
            }
        }

        return null;
    }

    @Override
    public String dropbox(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof String ) {
                return (String) val;
            }
        }

        return null;
    }

}
