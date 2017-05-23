package Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

import pineapple.ezscore.LoginActivity;
import pineapple.ezscore.MainActivity;
import pineapple.ezscore.MyMatchesActivity;
import pineapple.ezscore.R;

/**
 * Created by rasmusmadsen on 15/05/2017.
 */

public class DrawerListStuff {

    private final static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     * Initialize the ListView attached to the Navigation Drawer and set OnItemClickListener to the ListView
     * @param context
     * @param activity
     * @param listView
     * @return listview with clicklistener attached
     */
    public static ListView initList(Context context, Activity activity, ListView listView) {
        listView.setAdapter(getAdapter(context));
        listView.setOnItemClickListener(drawerClickListener(context));

        if (mAuth.getCurrentUser() != null) {
            listView.addHeaderView(getHeaderView(context));
            setUserName(activity);
        }

        return listView;
    }

    /**
     * Returns an ArrayAdapter of strings with menu items, both for when a user is logged in
     * and when no user is logged in
     * @param context
     * @return arrayadapter of strings for the menu
     */
    private static ArrayAdapter<String> getAdapter(final Context context) {
        FirebaseUser user = mAuth.getCurrentUser();
        String[] menuItems;
        if (user == null) {
            String[] mMenuItems = {"All Matches", "Login"};
            menuItems = mMenuItems;
        } else {
            String[] mMenuItems = {"All Matches", "My Matches", "Logout"};
            menuItems = mMenuItems;
        }
        return new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, menuItems);
    }

    /**
     * Returns a clickListener on the Navigation Drawer for when menu is clicked made of switch-statements
     * @param context
     * @return clicklistener for Drawer Navigation
     */
    private static AdapterView.OnItemClickListener drawerClickListener(final Context context) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getItemAtPosition(i).toString().toLowerCase()) {
                    case "all matches":
                        context.startActivity(new Intent(context, MainActivity.class));
                        break;
                    case "my matches":
                        context.startActivity(new Intent(context, MyMatchesActivity.class));
                        break;
                    case "logout":
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("logout", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        break;
                    case "login":
                        context.startActivity(new Intent(context, LoginActivity.class));
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * Return a headerview for the navigation drawer
     * @param context
     * @return view for header
     */
    private static View getHeaderView(final Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.header_view, null, false);
    }

    /**
     * Set the username of the user logged in to the header
     * @param activity
     */
    private static void setUserName(Activity activity){
        TextView txtUserEmail = (TextView) activity.findViewById(R.id.txtUserEmail);
        if (mAuth.getCurrentUser() != null) {
            txtUserEmail.setText(mAuth.getCurrentUser().getEmail());
        }
    }
}
