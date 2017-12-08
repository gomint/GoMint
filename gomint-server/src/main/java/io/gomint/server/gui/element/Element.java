package io.gomint.server.gui.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class Element {

    @Getter
    private final String id;
    @Getter
    private final String text;

    public JSONObject toJSON() {
        JSONObject element = new JSONObject();
        element.put( "text", this.text );
        return element;
    }

}
