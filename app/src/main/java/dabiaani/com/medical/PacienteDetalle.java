package dabiaani.com.medical;


import android.animation.Animator;
        import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.ProgressDialog;

        import android.os.AsyncTask;
        import android.os.Bundle;

        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
import android.view.Gravity;
import android.view.View;
        import android.view.ViewGroup;
        import android.view.ViewTreeObserver;
        import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

        import AsynkData.Alergias;
        import AsynkData.Pacientes;
        import AsynkData.ServiceHandler;
        import cz.msebera.android.httpclient.NameValuePair;
        import cz.msebera.android.httpclient.message.BasicNameValuePair;


/**
 * Created by Mario on 27/10/2015.
 */
public class PacienteDetalle extends AppCompatActivity {
    private Toolbar toolbar;
    private String TAG = "PacienteDetalle";
    private TextView tv_idPaciente;
    private TextView tv_nomPaciente;
    private TextView tv_genero;
    private TextView tv_nacPaciente;
    private TextView tv_profPaciente;
    private TextView tv_telPaciente;
    private TextView tv_celPaciente;
    private TextView tv_sangrePaciente;
    private TextView tv_antecedentes;
    private TextView tv_mailPaciente;
    ProgressDialog pDialog,progressDialog;
    ArrayList<NameValuePair> registros;
    ArrayList<NameValuePair> registrosAlergias;
    String ID_DOCTOR="";
    private String URL = "http://www.meustech.com:8080/medical_new/modelo/db/DBPacientesDetalleAndroid.php";
    private String URL_Alergias = "http://www.meustech.com:8080/medical_new/modelo/db/DBalergiasPacienteAndroid.php";
    String idpaciente;

    LinearLayout mLinearLayout;
    LinearLayout mLinearLayoutAnt ;
    LinearLayout mLinearLayoutPad ;
    LinearLayout mLinearLayoutHeader,mLinearLayoutHeaderAnt , mLinearLayoutHeaderPad ;
    ValueAnimator mAnimator;
    ValueAnimator mAnimatorB;
    ValueAnimator mAnimatorC;
    private ArrayList<Alergias> alergiasList;
    private ArrayList<Pacientes> pacientesList;

    LinearLayout rl,rl_pad ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_detalle);
        rl = (LinearLayout) findViewById(R.id.expandable);
        rl_pad = (LinearLayout) findViewById(R.id.expandablePadecimientos);
        alergiasList = new ArrayList<>();
        pacientesList = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Detalle del Paciente");
        tv_nomPaciente = (TextView) findViewById(R.id.pacienteNom);
        tv_nacPaciente = (TextView) findViewById(R.id.pacienteFecha);
        tv_sangrePaciente = (TextView) findViewById(R.id.pacienteTipoSan);
        tv_profPaciente = (TextView) findViewById(R.id.pacienteTipoSan);
        tv_telPaciente = (TextView) findViewById(R.id.pacienteTel);
        tv_celPaciente = (TextView) findViewById(R.id.pacienteCel);
        tv_mailPaciente = (TextView) findViewById(R.id.pacienteMail);
        tv_antecedentes = (TextView) findViewById(R.id.antecedentes);

        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();

        Bundle extras = getIntent().getExtras();
        idpaciente = extras.getString("idpaciente");

        new GetPacientesDetalle().execute();
        new GetAlergiasPaciente().execute();

        /*******************************EXPANDABLE CARDVIEWS*****************************/

        mLinearLayoutHeader = (LinearLayout) findViewById(R.id.header);
        mLinearLayout = (LinearLayout) findViewById(R.id.expandable);


        mLinearLayoutHeaderAnt = (LinearLayout) findViewById(R.id.headerAntecedentes);
        mLinearLayoutAnt = (LinearLayout) findViewById(R.id.expandableAntecedentes);

        mLinearLayoutHeaderPad = (LinearLayout) findViewById(R.id.headerPadecimientos);
        mLinearLayoutPad = (LinearLayout) findViewById(R.id.expandablePadecimientos);

        mLinearLayoutAnt.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mLinearLayoutAnt.getViewTreeObserver().removeOnPreDrawListener(this);
                        mLinearLayoutAnt.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        mLinearLayoutAnt.measure(widthSpec, heightSpec);

                        mAnimatorB = slideAnimatorAnt(0, mLinearLayoutAnt.getMeasuredHeight());
                        return true;
                    }
                });

        mLinearLayoutHeaderAnt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLinearLayoutAnt.getVisibility() == View.GONE) {
                    expandAntecedentes();
                } else {
                    collapseAntecendentes();
                }
            }
        });


        mLinearLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mLinearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        mLinearLayout.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        mLinearLayout.measure(widthSpec, heightSpec);

                        mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
                        return true;
                    }
                });

        mLinearLayoutHeader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility() == View.GONE) {
                    expand();
                } else {
                    collapse();
                }
            }
        });

        mLinearLayoutPad.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mLinearLayoutPad.getViewTreeObserver().removeOnPreDrawListener(this);
                        mLinearLayoutPad.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        mLinearLayoutPad.measure(widthSpec, heightSpec);

                        mAnimatorC = slideAnimatorPad(0, mLinearLayoutPad.getMeasuredHeight());
                        return true;
                    }
                });

        mLinearLayoutHeaderPad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLinearLayoutPad.getVisibility() == View.GONE) {
                    expandPadecimientos();
                } else {
                    collapsePadecimientos();
                }
            }
        });



    }
    private void DatosPacientes(){
        int val1 = 1;
        int val2= 1;
        for(int i = 0 ; i<pacientesList.size();i++){
            tv_nomPaciente.setText(pacientesList.get(i).getNombre()+" "+pacientesList.get(i).getApellidos());
            tv_genero.setText("Sexo : "+pacientesList.get(i).getSexo());
            tv_nacPaciente.setText("Fecha de Nacimiento : "+pacientesList.get(i).getFecha_nac());
            tv_sangrePaciente.setText("Tipo de Sangre : "+pacientesList.get(i).getTipo_sangre());
            tv_profPaciente.setText("Profesion : "+pacientesList.get(i).getProfesion());
            tv_telPaciente.setText("Telefono :"+pacientesList.get(i).getTel());
            tv_celPaciente.setText("Celular :"+pacientesList.get(i).getCel());
            tv_mailPaciente.setText("Correo :"+pacientesList.get(i).getCorreo());
            tv_antecedentes.setText("* "+pacientesList.get(i).getAntecedentes());


            if(pacientesList.get(i).getDiabetes()!= "0")
            {
                TextView txtPadecimientosDiab = new TextView(this);
                txtPadecimientosDiab.setId(val1);
                txtPadecimientosDiab.setEms(10);
                txtPadecimientosDiab.setText(pacientesList.get(i).getDiabetes());
                txtPadecimientosDiab.setTextSize(14);
                rl_pad.addView(txtPadecimientosDiab);
            }

            if(pacientesList.get(i).getHipertencion() != "0")
            {
                TextView txtPadecimientosHipe = new TextView(this);
                txtPadecimientosHipe.setId(val2);
                txtPadecimientosHipe.setEms(10);
                txtPadecimientosHipe.setText(pacientesList.get(i).getHipertencion());
                txtPadecimientosHipe.setTextSize(14);
                rl_pad.addView(txtPadecimientosHipe);
            }

            val2 ++;
            val1++;
        }
    }
    private void DatosAlergias(){

        int contador = 1;
        for (int x =0 ; x < alergiasList.size(); x++){
        //Log.d(TAG, "alergias " + alergiasList.get(x).getNombre());
            TextView txtAlergias = new TextView(this);
            txtAlergias.setId(contador);
            txtAlergias.setEms(10);
            txtAlergias.setText("* " +alergiasList.get(x).getTipo()+" - "+alergiasList.get(x).getNombre());
            txtAlergias.setTextSize(14);
            rl.addView(txtAlergias);

        contador++;
        }

    }
    private void expand() {
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);
//		 Remove and used in preDrawListener

		final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		mLinearLayout.measure(widthSpec, heightSpec);
		mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
        mAnimator.start();
    }
    private void expandAntecedentes() {
        //set Visible
        mLinearLayoutAnt.setVisibility(View.VISIBLE);
//		 Remove and used in preDrawListener

		final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		mLinearLayoutAnt.measure(widthSpec, heightSpec);
		mAnimatorB = slideAnimator(0, mLinearLayoutAnt.getMeasuredHeight());
        mAnimatorB.start();
    }
    private void expandPadecimientos() {
        //set Visible
        mLinearLayoutPad.setVisibility(View.VISIBLE);
//		 Remove and used in preDrawListener

		final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		mLinearLayoutPad.measure(widthSpec, heightSpec);
		mAnimatorC = slideAnimator(0, mLinearLayoutPad.getMeasuredHeight());
        mAnimatorC.start();
    }
    private void collapse() {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }
    private void collapseAntecendentes() {
        int finalHeight = mLinearLayoutAnt.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayoutAnt.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }
    private void collapsePadecimientos() {
        int finalHeight = mLinearLayoutPad.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayoutPad.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }
    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    private ValueAnimator slideAnimatorAnt(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayoutAnt.getLayoutParams();
                layoutParams.height = value;
                mLinearLayoutAnt.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    private ValueAnimator slideAnimatorPad(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayoutPad.getLayoutParams();
                layoutParams.height = value;
                mLinearLayoutPad.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    class GetPacientesDetalle extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PacienteDetalle.this);
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            tv_genero = (TextView) findViewById(R.id.pacienteGenero);
            ServiceHandler jsonParser = new ServiceHandler();

            registros = new ArrayList<NameValuePair>();
            registros.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            registros.add(new BasicNameValuePair("idpaciente", idpaciente));

            String json = jsonParser.makeServiceCall(URL, ServiceHandler.POST,registros);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);

                    if (jsonObj != null) {
                        JSONArray registro = jsonObj.getJSONArray("paciente");
                        for (int i = 0; i < registro.length(); i++) {
                            JSONObject paciente = registro.getJSONObject(i);
                            //datos cardview principal
                            pacientesList.add(new Pacientes(
                                    paciente.getInt("id"),
                                    paciente.getString("nombre"),
                                    paciente.getString("apellidos"),
                                    paciente.getString("fecha_nac"),
                                    paciente.getString("tipo_sangre"),
                                    paciente.getString("tel"),
                                    paciente.getString("cel"),
                                    paciente.getString("correo"),
                                    paciente.getString("notas"),
                                    paciente.getString("hipertencion"),
                                    paciente.getString("diabetes"),
                                    paciente.getString("sexo"),
                                    paciente.getString("antecedentes"),
                                    paciente.getString("profesion"))
                            );

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
                DatosPacientes();
            }
        }
    }
    class GetAlergiasPaciente extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PacienteDetalle.this);
            progressDialog.setMessage("Cargando Datos");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            registrosAlergias = new ArrayList<NameValuePair>();
            registrosAlergias.add(new BasicNameValuePair("idpaciente", idpaciente));

            String AlergiasJson = jsonParser.makeServiceCall(URL_Alergias, ServiceHandler.POST,registrosAlergias);

            if (AlergiasJson != null) {
                try {
                    JSONObject jsonAlergias = new JSONObject(AlergiasJson);



                    if (jsonAlergias != null) {
                        JSONArray registroAlergias = jsonAlergias.getJSONArray("alergias");

                        for (int i = 0 ; i <registroAlergias.length();i++){

                            JSONObject alergiasObj = registroAlergias.getJSONObject(i);
                            alergiasList.add(new Alergias(
                                    alergiasObj.getInt("id"),
                                    alergiasObj.getString("nombre"),
                                    alergiasObj.getString("notas"),
                                    alergiasObj.getString("tipo")));
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
        protected void onPostExecute(Void  result) {
            super.onPostExecute(result);
          if (progressDialog.isShowing()) {
              progressDialog.dismiss();
              DatosAlergias();

            }

        }
    }

}
