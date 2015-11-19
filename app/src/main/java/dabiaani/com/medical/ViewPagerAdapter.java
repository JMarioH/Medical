package dabiaani.com.medical;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;
/**
 * Created by Mario on 29/09/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    int Opcion;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb , int opcion) {
        super(fm);
        this.Opcion = opcion;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:  //  TAB posicion 1 para todas las pantallas
                if(Opcion == 1) {
                    return new Tab1();
                }else if(Opcion == 2){
                    return new Tab3();
                }else if(Opcion == 3){
                    return new Tab5();
                }
            case 1: //  TAB posicion 2 para todas las pantallas
                if(Opcion == 1) {
                    return new Tab2();
                }else if(Opcion == 2 ){
                    return new Tab4();
                }else if(Opcion == 3) {
                    return new Tab6();
                }
            default:
                return new Tab1();
        }

    }
    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

}

