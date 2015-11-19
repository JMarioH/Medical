package dabiaani.com.medical;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AsynkData.ServiceHandler;
import AsynkData.TipoEgresos;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Mario on 29/09/2015.
 */
public class Tab5 extends Fragment implements AdapterView.OnItemSelectedListener {
    private String TAG = "Egresos : ";
    private Spinner sp_egresos;

    private TextView editFechaEgr;
    private ImageButton button;
    private TextView editMontoEgr;
    private TextView editComprobante;
    private TextView editNotaEgr;
    private TextView editOtroEgreso;

    private ArrayList<TipoEgresos> egresosList;
    private ArrayList<NameValuePair> egresosPost;
    private ArrayList<NameValuePair> regPost;
    FloatingActionButton FAB;

    ProgressDialog pDialog;
    private String URL_TIPO_EGR = "http://www.meustech.com:8080/medical_new/modelo/db/DBEgresosAndroid.php";
    private String URL_EGR = "http://www.meustech.com:8080/medical_new/modelo/db/DBInsertEgresosAndroid.php";

    private String TAG_SUCCESS = "success";
    private int success;

    private String ID_DOCTOR = "" ;
    private int IDDOC = 1;

    String tipo_egreso ;
    String otro_egreso ;
    String comprobante ;
    String monto_egreso ;
    String fecha_egreso ;
    String notas_egreso ;
    String cat_egreso;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab5, container, false);
        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();

        editFechaEgr = (TextView) v.findViewById(R.id.fechaEgreso);
        sp_egresos = (Spinner) v.findViewById(R.id.sp_egresos);
        editComprobante = (TextView) v.findViewById(R.id.editComprobante);

        editMontoEgr = (TextView) v.findViewById(R.id.editMontoEgreso);
        editNotaEgr = (TextView) v.findViewById(R.id.editNotasEgreso);

        editOtroEgreso = (TextView) v.findViewById(R.id.editOtroEgreso);
        editOtroEgreso.setVisibility(v.GONE);

        egresosList = new ArrayList<>();

        button = (ImageButton) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });
        FAB = (FloatingActionButton) v.findViewById(R.id.btnegreso);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                tipo_egreso = String.valueOf(sp_egresos.getSelectedItem());
                comprobante = editComprobante.getText().toString();
                monto_egreso = editMontoEgr.getText().toString();
                if(!tipo_egreso.isEmpty() && !comprobante.isEmpty() && !monto_egreso.isEmpty()){
                    new Setegresos().execute();
                }else {
                    Toast.makeText(getActivity(), "Debe Proporcionar un tipo de egreso, \n comprobante y monto de egreso ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new GetEgresos().execute();
        return v;
    }
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            editFechaEgr.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < egresosList.size(); i++) {
            lables.add(egresosList.get(i).getNombre());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lables);
        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_egresos.setAdapter(spinnerAdapter);
        sp_egresos.setOnItemSelectedListener(new PikerOnItemSelectedListener());
    }

    class GetEgresos extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonEgreos = new ServiceHandler();
            regPost = new ArrayList<NameValuePair>();
            regPost.add(new BasicNameValuePair("iddoctor", ID_DOCTOR));
            String json = jsonEgreos.makeServiceCall(URL_TIPO_EGR, ServiceHandler.POST,regPost);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray ingresos = jsonObj.getJSONArray("tipo_egreso");
                        for (int i = 0; i < ingresos.length(); i++) {
                            JSONObject catObj = (JSONObject) ingresos.get(i);
                            TipoEgresos cat = new TipoEgresos(catObj.getInt("id"),catObj.getString("nombre"), catObj.getInt("iddoctor"));
                            egresosList.add(cat);
                        }
                        egresosList.add(new TipoEgresos(0,"OTRO",IDDOC));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                populateSpinner();
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(
                getActivity(),
                parent.getItemAtPosition(position).toString() + " Selected",
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public class PikerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tipoSeleccionado = parent.getItemAtPosition(position).toString();

            switch (tipoSeleccionado){
                case "OTRO":
                    editOtroEgreso.setVisibility(View.VISIBLE);
                    break;
                default:
                    editOtroEgreso.setVisibility(View.GONE);
                break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    class Setegresos extends AsyncTask<String ,String, String >{

        @Override
        protected String doInBackground(String... params) {
            tipo_egreso = String.valueOf(sp_egresos.getSelectedItem());
            otro_egreso = editOtroEgreso.getText().toString();
            comprobante = editComprobante.getText().toString();
            monto_egreso = editMontoEgr.getText().toString();
            fecha_egreso = editFechaEgr.getText().toString();
            notas_egreso = editNotaEgr.getText().toString();
            cat_egreso = "1";

            egresosPost = new ArrayList<NameValuePair>();
            egresosPost.add(new BasicNameValuePair("tipo_egreso",tipo_egreso));
            egresosPost.add(new BasicNameValuePair("otro_egreso",otro_egreso));
            egresosPost.add(new BasicNameValuePair("comprobante",comprobante));
            egresosPost.add(new BasicNameValuePair("monto",monto_egreso));
            egresosPost.add(new BasicNameValuePair("fecha_egreso",fecha_egreso));
            egresosPost.add(new BasicNameValuePair("notas",notas_egreso));
            egresosPost.add(new BasicNameValuePair("cat_egreso",cat_egreso));
            egresosPost.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            ServiceHandler jsonIng = new ServiceHandler();
            String respJson = jsonIng.makeServiceCall(URL_EGR, ServiceHandler.POST, egresosPost);
            Log.d(TAG,"Response ->"+ respJson);
            try{
                JSONObject Obj = new JSONObject(respJson);
                success = Obj.getInt(TAG_SUCCESS);
               // Log.d(TAG,"Success->"+ success );
                if (success == 1){}
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Guardando Datos del Egreso");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pDialog.isShowing()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();


            }
        }


    }
}