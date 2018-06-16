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
public class StepSlider extends Element implements io.gomint.gui.element.StepSlider {

    private final CustomForm form;
    private List<String> steps = new ArrayList<>();
    private int defaultStep = 0;

    public StepSlider( CustomForm form, String id, String text ) {
        super( id, text );
        this.form = form;
    }

    @Override
    public io.gomint.gui.element.StepSlider addStep( String step ) {
        this.steps.add( step );
        this.form.setDirty();
        return this;
    }

    @Override
    public io.gomint.gui.element.StepSlider addStep( String step, boolean defaultStep ) {
        if ( defaultStep ) {
            this.defaultStep = this.steps.size();
        }

        this.steps.add( step );
        this.form.setDirty();
        return null;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put( "type", "step_slider" );

        JSONArray jsonSteps = new JSONArray();
        for ( String step : this.steps ) {
            jsonSteps.add( step );
        }

        obj.put( "steps", jsonSteps );
        obj.put( "default", this.defaultStep );
        return obj;
    }

    @Override
    public Object getAnswer( Object answerOption ) {
        String answer = (String) answerOption;
        this.defaultStep = this.steps.indexOf( answer );
        return answer;
    }

}
