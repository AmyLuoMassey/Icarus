package project.com.icarus;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import project.com.icarus.db.DatabaseHandler;
import project.com.icarus.db.Location;
import project.com.icarus.db.Task;
import project.com.icarus.helpers.Helpers;
import project.com.icarus.ui.barcode.BarcodeFragment;
import project.com.icarus.ui.home.HomeFragment;
import project.com.icarus.ui.warehouse.WarehouseFragment;

public class MainActivity extends AppCompatActivity  implements BarcodeFragment.OnBarcodeInteractionListener, HomeFragment.OnLoginListener {

    private AppBarConfiguration mAppBarConfiguration;
    private String _userName;
    NavigationView navigationView;
    NavController navController;
    Toolbar toolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHandler dbHandler = new DatabaseHandler(getBaseContext(), null, null, 1);
                ArrayList<Task> taskList = dbHandler.loadTasks(_userName);
                String snackList = "";
                for (Task task : taskList) {
                    snackList = snackList + (task.getTaskName() + " " + Helpers.fromDate(task.getScheduledDate()) + "\n");
                }

                Snackbar.make(view, snackList, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,  R.id.nav_admin,R.id.nav_warehouse, R.id.nav_reporting)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Menu nav_Menu = navigationView.getMenu();
        fab.setVisibility(View.INVISIBLE);
        nav_Menu.findItem(R.id.nav_home).setVisible(true);
        nav_Menu.findItem(R.id.nav_admin).setVisible(true);
        nav_Menu.findItem(R.id.nav_reporting).setVisible(false);
        nav_Menu.findItem(R.id.nav_warehouse).setVisible(false);
        nav_Menu.findItem(R.id.nav_task).setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onLogin(String userName, boolean isAdmin) {
      _userName = userName;
        Menu nav_Menu = navigationView.getMenu();
        fab.setVisibility(View.VISIBLE);
        nav_Menu.findItem(R.id.nav_home).setVisible(false);
        nav_Menu.findItem(R.id.nav_reporting).setVisible(true);
        nav_Menu.findItem(R.id.nav_warehouse).setVisible(true);
        nav_Menu.findItem(R.id.nav_task).setVisible(true);

        if (isAdmin) {
            nav_Menu.findItem(R.id.nav_admin).setVisible(true);
        }
        navController.navigate(R.id.nav_warehouse);

   }




    @Override
    public void onBarcodeInteraction(String barcode) {

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        WarehouseFragment wf = (WarehouseFragment)navHostFragment.getChildFragmentManager().getFragments().get(0);
        wf.setBarcode(barcode);

    }
    @Override
    public void onPopupClose() {

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        WarehouseFragment wf = (WarehouseFragment)navHostFragment.getChildFragmentManager().getFragments().get(0);
        wf.closePopup();
    }
}