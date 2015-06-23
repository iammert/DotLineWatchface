package model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import co.mobiwise.dotlinewatchface.R;

/**
 * Created by mertsimsek on 23/06/15.
 */
public class DotLinePreferences {

    private static final String NAME = "DotLinePreferences";
    private static final String KEY_BACKGROUND_COLOUR = NAME + ".KEY_BACKGROUND_COLOUR";

    private final SharedPreferences mPreferences;
    private Context mContext;

    public static DotLinePreferences newInstance(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return new DotLinePreferences(preferences, mContext);
    }

    DotLinePreferences(SharedPreferences mPreferences, Context mContext) {
        this.mPreferences = mPreferences;
        this.mContext = mContext;
    }

    public int getBackgroundColour() {
        return mPreferences.getInt(KEY_BACKGROUND_COLOUR, mContext.getResources().getColor(R.color.color_default));
    }

    public void setBackgroundColour(int color) {
        mPreferences.edit().putInt(KEY_BACKGROUND_COLOUR, color).apply();
    }
}
