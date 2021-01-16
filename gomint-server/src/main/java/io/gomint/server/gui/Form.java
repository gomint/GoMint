package io.gomint.server.gui;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Form<R> implements io.gomint.gui.Form<R> {

    private final String title;
    private String icon = null;

    // Caching
    protected JSONObject cache;
    protected boolean dirty;

    public Form(String title) {
        this.title = title;
    }

    /**
     * Get the type of form we have right here
     *
     * @return type of form
     */
    public abstract String getFormType();

    @Override
    public Form<R> icon(String icon ) {
        this.icon = icon;
        this.dirty = true;
        return this;
    }

    @Override
    public String icon() {
        return this.icon;
    }

    @Override
    public String title() {
        return this.title;
    }

    /**
     * Get the JSON representation of this form
     *
     * @return ready to be sent JSON
     */
    public JSONObject toJSON() {
        // Basic data
        JSONObject obj = new JSONObject();
        obj.put( "type", this.getFormType() );
        obj.put( "title", this.title() );
        obj.put( "content", new JSONArray() );

        // Check if we have a icon
        if ( this.icon != null ) {
            JSONObject jsonIcon = new JSONObject();
            jsonIcon.put( "type", this.icon.startsWith( "http" ) || this.icon.startsWith( "https" ) ? "url" : "path" );
            jsonIcon.put( "data", this.icon );
            obj.put( "icon", jsonIcon );
        }

        return obj;
    }

    /**
     * Parse the given response into the correct listener format
     *
     * @param json data from the client
     * @return correct formatted object for the listener
     */
    public abstract R parseResponse( String json );

    public Form<R> dirty() {
        this.dirty = true;
        return this;
    }

}
