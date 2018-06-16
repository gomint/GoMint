package io.gomint.server.gui.element;

import io.gomint.server.gui.CustomForm;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Dropdown extends Element implements io.gomint.gui.element.Dropdown {

    private final CustomForm form;
    private List<String> options = new ArrayList<>();
    private int defaultOption = 0;

    public Dropdown( CustomForm customForm, String id, String text ) {
        super( id, text );
        this.form = customForm;
    }

    @Override
    public io.gomint.gui.element.Dropdown addOption( String option ) {
        this.options.add( option );
        this.form.setDirty();
        return this;
    }

    @Override
    public io.gomint.gui.element.Dropdown addOption( String option, boolean defaultOption ) {
        if ( defaultOption ) {
            this.defaultOption = this.options.size();
        }

        this.options.add( option );
        this.form.setDirty();
        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put( "type", "dropdown" );

        JSONArray jsonOptions = new JSONArray();
        for ( String option : this.options ) {
            jsonOptions.add( option );
        }

        obj.put( "options", jsonOptions );
        obj.put( "default", this.defaultOption );
        return obj;
    }

    @Override
    public Object getAnswer( Object answerOption ) {
        long optionIndex = (long) answerOption;
        this.defaultOption = (int) optionIndex;
        return this.options.get( (int) optionIndex );
    }

}
