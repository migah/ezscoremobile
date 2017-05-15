package Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pineapple.ezscore.LoginActivity;
import pineapple.ezscore.MainActivity;
import pineapple.ezscore.MyMatchesActivity;

/**
 * Created by rasmusmadsen on 15/05/2017.
 */

public class DrawerListStuff {

    private final static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static ArrayAdapter<String> getAdapter(final Context context) {
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

    public static AdapterView.OnItemClickListener drawerClickListener(final Context context) {
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
}
