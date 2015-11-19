package dabiaani.com.medical;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import SlideTabs.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {

    //Define Variables para Componene
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    //Define Varibles Texto
    String IdDoctor;
    String nomDoctor;
    TextView nombreHeader;

    Integer opcion ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        if(extras != null ){
            IdDoctor = extras.getString("iddoctor");
            nomDoctor = extras.getString("nomdoctor");

        }else{
            IdDoctor = null;
            nomDoctor = null;

        }
        GlobalClass globalVariable = GlobalClass.getInstance();
        globalVariable.setIddoctor(IdDoctor);
        globalVariable.setNomDoctor(nomDoctor);

        nombreHeader = (TextView) findViewById(R.id.txt_header_nombre);
        nombreHeader.setText("Dr. " + nomDoctor);

        opcion = 1 ;
        CharSequence Titles_pacientes[]={"Alta Pacientes","Pacientes Reg."};
        int NumTabsPacientes = 2;

        inicializaComponentes(Titles_pacientes, NumTabsPacientes, opcion);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.nvView);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_pacientes:
                        opcion = 1;

                        CharSequence Titles_pacientes[] = {"Alta Pacientes", "Pacientes Reg."};
                        int NumTabsPacientes = 2;
                        inicializaComponentes(Titles_pacientes, NumTabsPacientes, opcion);
                        //Toast.makeText(getApplicationContext(), "Seccion Paciente", Toast.LENGTH_SHORT).show();
                       // adapter.notifyDataSetChanged();
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.nav_ingresos:
                        opcion = 2;

                        CharSequence Titles_ingresos[] = {"Alta Ingresos", "Ingresos Reg."};
                        int NumbTabsIngresos = 2;
                        inicializaComponentes(Titles_ingresos, NumbTabsIngresos, opcion);
                       // adapter.notifyDataSetChanged();
                       // Toast.makeText(getApplicationContext(), "Seccion Ingresos", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_egresos:
                        opcion = 3;

                        CharSequence Titles_egresos[] = {"Alta Egresos", "Egresos Reg"};
                        int NumbTabsEgresos = 2;
                        inicializaComponentes(Titles_egresos, NumbTabsEgresos, opcion);

                        Toast.makeText(getApplicationContext(), "Seccion Egresos", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_log_out:

                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);

                    default:
                      //  Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    //<editor-fold desc="Menu Opctions">
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>

    public void inicializaComponentes(CharSequence Titles[], int Numboftabs, int opcion){
        // Initializing Toolbar and setting it as the actionbar
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs,opcion);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
       // adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}