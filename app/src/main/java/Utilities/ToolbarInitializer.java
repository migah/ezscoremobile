package Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import pineapple.ezscore.MatchActivity;
import pineapple.ezscore.NewMatchActivity;
import pineapple.ezscore.R;

/**
 * Created by rasmusmadsen on 15/05/2017.
 */

public class ToolbarInitializer {

    /**
     * Initiates a toolbar
     * @param context the context
     * @param toolbar the toolbar to initiate
     * @param layout a DrawerLayout, which opens when the navigation button is pressed
     * @return the toolbar
     */
    public static Toolbar initToolbar(Context context, Toolbar toolbar, final DrawerLayout layout) {
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_48dp);
        toolbar.setNavigationOnClickListener(getNavigationClickListener(layout));
        toolbar.setOnMenuItemClickListener(getItemClickListener(context));
        return toolbar;
    }

    /**
     * Initializes the menu clickListener
     * @param context the context
     * @return the MenuItemClickListener
     */
    private static Toolbar.OnMenuItemClickListener getItemClickListener(final Context context) {
        return new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getTitle().toString().toLowerCase()) {
                    case "add match":
                        context.startActivity(new Intent(context, NewMatchActivity.class));
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
    }

    /**
     * Return a clickListener for the navigation button, which opens a drawer
     * @param layout Which drawer to open
     * @return a clickListener
     */
    private static View.OnClickListener getNavigationClickListener(final DrawerLayout layout) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.openDrawer(GravityCompat.START);
            }
        };
    }

}
