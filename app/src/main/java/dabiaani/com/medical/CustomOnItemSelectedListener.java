package dabiaani.com.medical;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


/**
 * Created by Mario on 05/10/2015.
 */
public class CustomOnItemSelectedListener implements OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      /*  Toast.makeText(parent.getContext(),
                "On Item Select : \n" + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
