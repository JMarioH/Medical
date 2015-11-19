package dabiaani.com.medical;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import AsynkData.TipoIngresos;
import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


/**
 * Created by Mario on 29/09/2015.
 */
public class Tab3 extends Fragment implements AdapterView.OnItemSelectedListener {
    String TAG = "TAB3";
    private Spinner sp_tipo_ing;
    private TextView editFechaIng;
    private ImageButton button;
    private TextView editNotaIngreso;
    private TextView editMontoIngreso;
    private ArrayList<TipoIngresos> ingresosList;
    ArrayList<NameValuePair> ingresosPost;
    ArrayList<NameValuePair> regPost;
    FloatingActionButton FAB;
    ProgressDialog pDialog;
    private String URL_TIP_ING = "http://www.meustech.com:8080/medical_new/modelo/db/DBIngresosAndroid.php";
    private String URL_ING = "http://www.meustech.com:8080/medical_new/modelo/db/DBInsertIngresosAndroid.php";
    private String TAG_SUCCESS = "success";
    private int success;
    private String ID_DOCTOR = "";
    String tipo_ing;
    String monto_ing;
    String fecha_ing;
    String notas_ing;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3, container, false);

        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();

        editFechaIng = (TextView) v.findViewById(R.id.text_date);
        sp_tipo_ing = (Spinner) v.findViewById(R.id.sp_ingresos);
        sp_tipo_ing.setOnItemSelectedListener(this);
        editNotaIngreso = (TextView) v.findViewById(R.id.editNotasIngresos);
        editMontoIngreso = (TextView) v.findViewById(R.id.editMontoIngreso);

        ingresosList = new ArrayList<>();

        button = (ImageButton) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });
        FAB = (FloatingActionButton) v.findViewById(R.id.btnIngresos);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                monto_ing = editMontoIngreso.getText().toString();
                fecha_ing = editFechaIng.getText().toString();
                if(!monto_ing.isEmpty() && !fecha_ing.isEmpty() ) {
                    new SetIngresos().execute();

                }else{
                    Toast.makeText(getActivity(), "Debe proporcionar un monto y fecha.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new GetIngresos().execute();

        return v;
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<>();
        List<Integer> lablesID = new ArrayList<>();
         for (int i = 0; i < ingresosList.size(); i++) {

            lables.add(ingresosList.get(i).getNombre());
            lablesID.add(ingresosList.get(i).getId());

        }
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, lables);
        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_tipo_ing.setAdapter(spinnerAdapter);

        sp_tipo_ing.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    class GetIngresos extends AsyncTask<Void, Void, Void> {
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
            ServiceHandler jsonParser = new ServiceHandler();
            regPost = new ArrayList<NameValuePair>();
            regPost.add(new BasicNameValuePair("iddoctor", ID_DOCTOR));
            String json = jsonParser.makeServiceCall(URL_TIP_ING, ServiceHandler.POST,regPost);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray ingresos = jsonObj.getJSONArray("tipo_ing");
                        for (int i = 0; i < ingresos.length(); i++) {
                            JSONObject catObj = (JSONObject) ingresos.get(i);
                            TipoIngresos cat = new TipoIngresos(catObj.getInt("id"), catObj.getString("nombre"), catObj.getInt("iddoctor"));
                            ingresosList.add(cat);
                        }
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
            editFechaIng.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(
                getActivity(),
                parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    class SetIngresos extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Guardando Datos del Ingreso ");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            tipo_ing = String.valueOf(sp_tipo_ing.getSelectedItem());

            monto_ing = editMontoIngreso.getText().toString();
            fecha_ing = editFechaIng.getText().toString();
            notas_ing = editNotaIngreso.getText().toString();

            ingresosPost = new ArrayList<NameValuePair>();
            ingresosPost.add(new BasicNameValuePair("tipo_ingreso", tipo_ing));
            ingresosPost.add(new BasicNameValuePair("monto", monto_ing));
            ingresosPost.add(new BasicNameValuePair("fecha_ing", fecha_ing));
            ingresosPost.add(new BasicNameValuePair("notas", notas_ing));
            ingresosPost.add(new BasicNameValuePair("iddoctor", ID_DOCTOR));

            ServiceHandler jsonIng = new ServiceHandler();

            String respJson = jsonIng.makeServiceCall(URL_ING, ServiceHandler.POST, ingresosPost);
            Log.d(TAG, respJson.toString());
                try {
                    JSONObject Obj = new JSONObject(respJson);
                    success = Obj.getInt(TAG_SUCCESS);
                    Log.d(TAG, " my success "+ success);
                    if (success == 1) {
                        //Toast.makeText(getContext(), "Ingreso Creado ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
        return null;
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