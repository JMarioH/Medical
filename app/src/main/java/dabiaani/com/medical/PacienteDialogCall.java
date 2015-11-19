package dabiaani.com.medical;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Mario on 17/11/2015.
 */
public class PacienteDialogCall extends DialogFragment{
    private String numero;
    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListener alertPositiveListener;

    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    interface AlertPositiveListener {
        public void onPositiveClick(int position);
    }

    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override

        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            Bundle bundle = getArguments();

            if(position==0){
                numero = bundle.getString("local");
            }else{
                numero = bundle.getString("celular");
            }

            llamar(numero);
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** Getting the arguments passed to this fragment */
        Bundle bundle = getArguments();

        int position = bundle.getInt("position");
        String local =   bundle.getString("local");
        String cel =   bundle.getString("celular");
        String[] code = new String[]{};

        if(local.equals("") ) {
             code = new String[]{cel};
        }else if(cel.equals("")){
             code = new String[]{local};
        }else{
             code = new String[]{local,cel};
        }




        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        /** Setting a title for the window */
        b.setTitle("Selecciona un numero para llamar'");
        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(code, position, null);

        /** Setting a positive button and its listener */
        b.setPositiveButton("LLamar", positiveListener);

        /** Setting a positive button and its listener */
        b.setNegativeButton("Cancel", null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();

        /** Return the alert dialog window */
        return d;
    }
    public static Object[] removeElements(String[] input, String deleteMe) {
        List result = new ArrayList();

        for(String item : input)
            if(!deleteMe.equals(item))
                result.add(item);

        return result.toArray(input);
    }

    public void llamar(String numero){
         Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
        try{
            startActivity(in);
        }

        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getActivity(), "No se pudo realizar esta accion, intente de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

}