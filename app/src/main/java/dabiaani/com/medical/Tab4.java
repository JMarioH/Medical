package dabiaani.com.medical;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AsynkData.Ingresos;
import AsynkData.IngresosAdapter;
import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Mario on 29/09/2015.
 */
public class Tab4 extends Fragment {
    private RecyclerView mRecyclerView;
    private IngresosAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Ingresos> ingresosList;

    public Tab4Listener Tab4listener;
    ArrayList<NameValuePair> post;

    ProgressDialog pDialog;
    public String URL = "http://www.meustech.com:8080/medical_new/modelo/db/DBIngresosListaAndroid.php";

    private String TAG = "TAB4 : ";
    private String ID_DOCTOR="";

    public interface Tab4Listener {
    }
    public Tab4() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4, container, false);
        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();
        ingresosList = new ArrayList<Ingresos>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rcv_ingresos);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new IngresosAdapter(ingresosList);
       // mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        new GetIngresos().execute();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Tab4Listener) {
            Tab4listener = (Tab4Listener) context;
        }
    }

    class GetIngresos extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL, ServiceHandler.POST,post);
         /*   Log.d(TAG,"resultParser"+json);*/
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);

                    if (jsonObj != null) {
                        JSONArray registro = jsonObj.getJSONArray("ingresosReg");
                        for (int i = 0; i < registro.length(); i++) {
                            JSONObject ingresoObj = registro.getJSONObject(i);
                            ingresosList.add(new Ingresos(
                                    ingresoObj.getInt("idingreso"),
                                    ingresoObj.getString("monto"),
                                    ingresoObj.getString("fecha"),
                                    ingresoObj.getString("notas"),
                                    ingresoObj.getString("tipo_ingreso"),
                                    ingresoObj.getInt("iddoctor"),
                                    ingresoObj.getInt("idcirugia"),
                                    ingresoObj.getInt("idconsulta")));
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
        private void DatosRecyclerView() {
            ingresosList = new ArrayList<Ingresos>();
            for (int i = 0; i < ingresosList.size(); i++) {
                ingresosList.add(new Ingresos(
                ingresosList.get(i).getId(),
                ingresosList.get(i).getMonto(),
                ingresosList.get(i).getFecha(),
                ingresosList.get(i).getNotas(),
                ingresosList.get(i).getTipo_ingreso(),
                ingresosList.get(i).getIddoctor(),
                ingresosList.get(i).getIdcirugia(),
                ingresosList.get(i).getIdconsulta())
                );



            }// fin del for


        }
        @Override
        protected void onPostExecute(Void Void) {
            super.onPostExecute(Void);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                DatosRecyclerView();
                mAdapter.notifyDataSetChanged();
            }
        }

    }

}
