package dabiaani.com.medical;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AsynkData.Egresos;
import AsynkData.EgresosAdapter;
import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Mario on 29/09/2015.
 */
public class Tab6 extends Fragment {
    private RecyclerView mRecyclerView;
    private EgresosAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Egresos> egresosList;

    public Tab6Listener Tab6listener;
    ArrayList<NameValuePair> post;

    ProgressDialog pDialog;


    private String TAG = "TAB#6 ";
    private String ID_DOCTOR="";
    public interface Tab6Listener{}
    public Tab6(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab6, container, false);
        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();
        egresosList = new ArrayList<Egresos>();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rcv_egresos);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new EgresosAdapter(egresosList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        new GetEgresos().execute();
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Tab6Listener) {
            Tab6listener = (Tab6Listener) context;
        }
    }

    private class GetEgresos extends AsyncTask<Void,Void,Void> {
        public String URL = "http://www.meustech.com:8080/medical_new/modelo/db/DBEgresosListaAndroid.php";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL, ServiceHandler.POST,post);

            if (json!= null){
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray registro = jsonObj.getJSONArray("egresosReg");
                        for (int i = 0; i < registro.length(); i++) {
                            JSONObject egresoObj = registro.getJSONObject(i);
                            egresosList.add(new Egresos(
                                    egresoObj.getInt("idegreso"),
                                    egresoObj.getString("comprobante"),
                                    egresoObj.getString("monto"),
                                    egresoObj.getString("fecha_egreso"),
                                    egresoObj.getString("fecha_reg"),
                                    egresoObj.getString("notas"),
                                    egresoObj.getString("tipo_egreso"),
                                    egresoObj.getString("cat_egreso")));
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }else {
                Log.e("JSON Data","No se puede Conectar con el Servidor");
            }
            return null;
        }
       private void DatosEgresos() {
            egresosList = new ArrayList<Egresos>();
            for (int i = 0; i < egresosList.size(); i++) {
                egresosList.add(new Egresos(
                                egresosList.get(i).getId(),
                                egresosList.get(i).getComprobante(),
                                egresosList.get(i).getMonto(),
                                egresosList.get(i).getFecha_egreso(),
                                egresosList.get(i).getFecha_reg(),
                                egresosList.get(i).getNotas(),
                                egresosList.get(i).getTipo_egreso(),
                                egresosList.get(i).getTipo_proveedor())
                );
            }
        }
        @Override
        protected void onPostExecute(Void Void) {
            super.onPostExecute(Void);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                DatosEgresos();
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}