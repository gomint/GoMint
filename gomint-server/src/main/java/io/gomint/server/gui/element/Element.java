package io.gomint.server.gui.element;

import org.json.simple.JSONObject;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Element {

    private final String id;
    private final String text;

    public Element(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    /**
     * Get the JSON representation of a form
     *
     * @return json representation of the form
     */
    public JSONObject toJSON() {
        JSONObject element = new JSONObject();
        element.put( "text", this.text );
        return element;
    }

    /**
     * Get the correct answer object for this form element
     *
     * @param answerOption object given from the client
     * @return correct answer object for the listener
     */
    public Object getAnswer( Object answerOption ) {
        return answerOption;
    }

}
