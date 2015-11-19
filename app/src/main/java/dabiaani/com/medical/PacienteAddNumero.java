package dabiaani.com.medical;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AsynkData.Response;
import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Mario on 18/11/2015.
 */
public class PacienteAddNumero extends DialogFragment {
    private EditText telefono;
    private String numero;
    private String idpaciente= "";
    private String URLSetNumero = "http://www.meustech.com:8080/medical_new/modelo/db/DBinsertNumeroPacienteAndroid.php";
    private String TAG = "DialogFragment ";
    private ArrayList<Response> response;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.paciente_call_dialog, null);
        Bundle bundle = getArguments();
        idpaciente = bundle.getString("idpaciente");
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Llamar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        telefono = (EditText) view.findViewById(R.id.DfNuevoNumero);
                        numero = telefono.getText().toString();
                        //TODO Guardar el numero marcado
                        llamar(numero);
                        if (!numero.isEmpty()) {
                            new SetNumberPaciente().execute();
                        }
                    }
                })
                .setNegativeButton("Regresar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PacienteAddNumero.this.getDialog().cancel();
                    }
                });
        return builder.create();
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
    private void responseSetNumero() {
        for (int i = 0; i < response.size() ;i++){
            Toast.makeText(getActivity(),""+response.get(i).getMensaje(),Toast.LENGTH_SHORT).show();
        }
    }
    class SetNumberPaciente extends AsyncTask<Void,Void,Void>{
        ArrayList<NameValuePair> datosPost;
        @Override
        protected Void doInBackground(Void... params) {
            datosPost = new ArrayList<NameValuePair>();
            ServiceHandler jsonIng = new ServiceHandler();
            datosPost.add(new BasicNameValuePair("idpaciente",idpaciente));
            datosPost.add(new BasicNameValuePair("numero",numero));

            String respJson = jsonIng.makeServiceCall(URLSetNumero, ServiceHandler.POST, datosPost);
            Log.d(TAG, respJson.toString());
            try {
                JSONObject Obj = new JSONObject(respJson);
                JSONArray registro = Obj.getJSONArray("numero_alta");
                for (int i = 0 ; i < registro.length(); i++){

                    JSONObject obj = (JSONObject)registro.get(i);
                    Response respuesta = new Response(obj.getString("success"),obj.getString("message"));
                    response.add(respuesta);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            responseSetNumero();
        }
    }


}