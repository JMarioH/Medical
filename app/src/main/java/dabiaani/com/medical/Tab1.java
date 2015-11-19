package dabiaani.com.medical;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Mario on 29/09/2015.
 */
public class Tab1 extends Fragment {

    private String URL = "http://www.meustech.com:8080/medical_new/modelo/db/DBInsertPacientesAndroid.php";

    ProgressDialog pDialog;
    FloatingActionButton FAB;
    EditText editNombre;
    EditText editAPellidos;
    EditText editFechaNac;
    EditText editTipoS;
    EditText editProfesion;
    EditText editTel;
    EditText editCel;
    EditText editMail;
    CheckBox checkBox_hiper;
    CheckBox checkBox_diabetes;
    CheckBox checkBox_M;
    CheckBox checkBox_F;
    EditText editNotas;
    ArrayList<NameValuePair> registros;
    ImageButton button;
    //Global Varibale
    TextView etNomDoc;
    //
    String nombre;
    String apellidos;
    String fecha;
    String profesion;
    String tipo;
    String telefono;
    String cel;
    String mail;
    String notas;

    String ID_DOCTOR="";
    String var1 = "0";
    String var2 = "0";
    String var3 = "";

    public Tab1(){}

    String nomDoctor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1, container, false);
        //variables Globales
        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();


        editNombre = (EditText) v.findViewById(R.id.editNombre);
        editAPellidos = (EditText) v.findViewById(R.id.editApellidos);
        editFechaNac = (EditText) v.findViewById(R.id.editFecha);
        editProfesion = (EditText) v.findViewById(R.id.editProfesion);
        editTipoS = (EditText) v.findViewById(R.id.editTiposangre);
        editTel = (EditText) v.findViewById(R.id.editTelefono);
        editCel = (EditText)v.findViewById(R.id.editCel);
        editMail = (EditText)v.findViewById(R.id.editMail);

        button = (ImageButton) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });

        checkBox_hiper = (CheckBox) v.findViewById(R.id.checkbox_hiper);
       // checkBox_hiper.setChecked(true);
        checkBox_diabetes = (CheckBox) v.findViewById(R.id.checkbox_diabetes);
       // checkBox_diabetes.setChecked(true);

        checkBox_F = (CheckBox) v.findViewById(R.id.sexo_f);
        checkBox_M = (CheckBox) v.findViewById(R.id.sexo_m);

        editNotas = (EditText) v.findViewById(R.id.editNotasPaciente);

        if(checkBox_hiper.isChecked()){
            checkBox_hiper.setChecked(false);
        }
        if(checkBox_diabetes.isChecked()){
            checkBox_diabetes.setChecked(false);
        }

        if(checkBox_F.isChecked()){
            checkBox_F.setChecked(false);
        }
        if(checkBox_M.isChecked()){
            checkBox_M.setChecked(true);
        }

        checkBox_hiper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox)v).isChecked();
                if (isChecked){
                  //  Toast.makeText(getContext(),"Hipertencion Seleccionada",Toast.LENGTH_SHORT).show();
                }else{
                  //    Toast.makeText(getContext(),"Hipertencion NoSeleccionada",Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox_diabetes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox)v).isChecked();
                if (isChecked){
                //    Toast.makeText(getContext(),"Diabetes Seleccionada",Toast.LENGTH_SHORT).show();
                }else{
                  //  Toast.makeText(getContext(),"Diabetes NoSeleccionada",Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBox_F.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox)v).isChecked();
                if(isChecked) {
                    checkBox_M.setChecked(false);
                }
            }
        });
        checkBox_M.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox)v).isChecked();
                if(isChecked){
                    checkBox_F.setChecked(false);

                }
            }
        });

        nombre = editNombre.getText().toString();
        apellidos = editAPellidos.getText().toString();
        fecha = editFechaNac.getText().toString();
        profesion = editProfesion.getText().toString();
        tipo = editTipoS.getText().toString();
        telefono = editTel.getText().toString();
        cel = editCel.getText().toString();
        mail = editMail.getText().toString();
        notas = editNotas.getText().toString();


        if(checkBox_hiper.isChecked()){
            var1 = "1" ;
        }

        if(checkBox_diabetes.isChecked()){
            var2 = "1" ;
        }

        if(checkBox_F.isChecked()){
            var3 = "F" ;
        }
        if(checkBox_M.isChecked()){
            var3 = "M" ;
        }

        FAB = (FloatingActionButton) v.findViewById(R.id.btnPaciente);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(!nombre.isEmpty() && !apellidos.isEmpty() && telefono.isEmpty() && cel.isEmpty()) {

                    new SetPaciente().execute();
                }else{
                    Toast.makeText(getActivity(), "Debe procporcionar un nombre y telefono/celular", Toast.LENGTH_SHORT).show();
                }

            }
        });
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

            editFechaNac.setText(String.valueOf(year)+"-"+String.valueOf(monthOfYear + 1)+"-"+String.valueOf(dayOfMonth));
        }
    };

    class SetPaciente extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Creando Paciente");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            registros = new ArrayList<NameValuePair>();
            registros.add(new BasicNameValuePair("nombre",nombre));
            registros.add(new BasicNameValuePair("apellidos",apellidos));
            registros.add(new BasicNameValuePair("fecha_nac", fecha));
            registros.add(new BasicNameValuePair("profesion", profesion));
            registros.add(new BasicNameValuePair("sexo",var3));
            registros.add(new BasicNameValuePair("tipo_sangre",tipo));
            registros.add(new BasicNameValuePair("tel",telefono));
            registros.add(new BasicNameValuePair("cel",cel));
            registros.add(new BasicNameValuePair("correo",mail));
            registros.add(new BasicNameValuePair("notas",notas));
            registros.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            registros.add(new BasicNameValuePair("hipertencion", var1 ));
            registros.add(new BasicNameValuePair("diabetes",var2));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL, ServiceHandler.POST,registros);
            try {

                JSONObject jsonObj = new JSONObject(json);
                String result = jsonObj.toString();
                if (result != null) {
                    Toast.makeText(getActivity(), "Paciente Guardado B ", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pDialog.isShowing()) {
                pDialog.dismiss();


            }
        }



    }
}