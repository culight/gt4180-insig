package edu.gatech.gt4823.presentation.splash_screen;

/**
 * Created by Demerrick on 11/8/2014.
 */
public class SimpleTableItem {

    /** The image resource ID associated with the table item. */
    public final int iconResId;

    /** The primary text associated with the table item. */
    public final CharSequence primaryText;

    /** The secondary text associated with the table item. */
    public final CharSequence secondaryText;

    /**
     * Initializes a new {@code SimpleTableItem} with the specified icon, primary text, and
     * secondary text.
     */
    public SimpleTableItem(int iconResId, CharSequence primaryText, CharSequence secondaryText) {
        this.iconResId = iconResId;
        this.primaryText = primaryText;
        this.secondaryText = secondaryText;
    }
}

