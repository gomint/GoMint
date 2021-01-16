package io.gomint.gui;

/**
 * @param <R> type of return value from the response
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface Form<R> {

    /**
     * Get the title of this form
     *
     * @return title of this form
     */
    String title();

    /**
     * Get the current configured icon
     *
     * @return path of the icon or null when none has been set
     */
    String icon();

    /**
     * Set new icon for this
     *
     * @param icon which should be used ( can be http / https )
     */
    Form<R> icon(String icon );

}
