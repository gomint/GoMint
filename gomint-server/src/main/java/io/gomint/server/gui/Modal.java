package io.gomint.server.gui;

import org.json.simple.JSONObject;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Modal extends Form<Boolean> implements io.gomint.gui.Modal {

    private final String question;
    private String trueButtonText;
    private String falseButtonText;

    public Modal( String title, String question ) {
        super( title );
        this.question = question;
    }

    @Override
    public Modal trueText(String text ) {
        this.trueButtonText = text;
        this.dirty = true;
        return this;
    }

    @Override
    public Modal falseText(String text ) {
        this.falseButtonText = text;
        this.dirty = true;
        return this;
    }

    @Override
    public String getFormType() {
        return "modal";
    }

    @Override
    public JSONObject toJSON() {
        // Fast out when cached
        if ( this.cache != null && !this.dirty ) {
            return this.cache;
        }

        // Create new JSON view of this form
        JSONObject jsonObject = super.toJSON();
        jsonObject.put( "content", this.question );
        jsonObject.put( "button1", this.trueButtonText );
        jsonObject.put( "button2", this.falseButtonText );

        // Cache and return
        this.cache = jsonObject;
        this.dirty = false;
        return this.cache;
    }

    @Override
    public Boolean parseResponse( String json ) {
        return json.trim().equals( "true" );
    }

}
