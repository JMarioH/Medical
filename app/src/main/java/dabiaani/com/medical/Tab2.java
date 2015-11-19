package dabiaani.com.medical;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import AsynkData.Pacientes;
import AsynkData.PacientesAdapter;
import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Mario on 29/09/2015.
 */
public class Tab2 extends Fragment {
    private RecyclerView mRecyclerView;
    private PacientesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Pacientes> pacientesList;
    public Tab2Listener listener;
    ProgressDialog pDialog;
    ArrayList<NameValuePair> registros;
    String ID_DOCTOR="";
    private String URL = "http://www.meustech.com:8080/medical_new/modelo/db/DBPacientesAndroid.php";

    private String TAG = "Pacientes Registrados";
    public interface Tab2Listener {
    }

    public Tab2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();


        pacientesList = new ArrayList<Pacientes>();


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rcv_paciente);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PacientesAdapter(pacientesList,getActivity());
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new PacientesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int idpaciente) {

                Intent intent = new Intent(getActivity(), PacienteDetalle.class);
                String paciente = String.valueOf(idpaciente);
                intent.putExtra("idpaciente",paciente);
                getActivity().startActivity(intent);

            }
        });


        new GetPacientes().execute();
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Tab2Listener) {
            listener = (Tab2Listener) context;
        }
    }
    class GetPacientes extends AsyncTask<Void, Void, Void> {
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
            registros = new ArrayList<NameValuePair>();
            registros.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            String json = jsonParser.makeServiceCall(URL, ServiceHandler.POST,registros);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);

                    if (jsonObj != null) {
                        JSONArray registro = jsonObj.getJSONArray("paciente");
                        for (int i = 0; i < registro.length(); i++) {
                           JSONObject catObj = registro.getJSONObject(i);
                            pacientesList.add(new Pacientes(
                                    catObj.getInt("id"),
                                    catObj.getString("nombre"),
                                    catObj.getString("apellidos"),
                                    catObj.getString("fecha_nac"),
                                    catObj.getString("tipo_sangre"),
                                    catObj.getString("tel"),
                                    catObj.getString("cel"),
                                    catObj.getString("correo"),
                                    catObj.getString("notas"),
                                    catObj.getString("hipertencion"),
                                    catObj.getString("diabetes"),
                                    catObj.getString("sexo"),
                                    catObj.getString("antecedentes"),
                                    catObj.getString("profesion")
                                    ));

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

        private void PopulateRecyclerView() {
            pacientesList = new ArrayList<Pacientes>();
            for (int i = 0; i < pacientesList.size(); i++) {
                pacientesList.add(new Pacientes(
                                pacientesList.get(i).getId(),
                                pacientesList.get(i).getNombre(),
                                pacientesList.get(i).getApellidos(),
                                pacientesList.get(i).getFecha_nac(),
                                pacientesList.get(i).getTipo_sangre(),
                                pacientesList.get(i).getTel(),
                                pacientesList.get(i).getCel(),
                                pacientesList.get(i).getCorreo(),
                                pacientesList.get(i).getNotas(),
                                pacientesList.get(i).getHipertencion(),
                                pacientesList.get(i).getDiabetes(),
                                pacientesList.get(i).getSexo(),
                                pacientesList.get(i).getAntecedentes(),
                                pacientesList.get(i).getProfesion()
                        )
                );


            }

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                PopulateRecyclerView();
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}