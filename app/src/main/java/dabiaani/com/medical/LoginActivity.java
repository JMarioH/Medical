package dabiaani.com.medical;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
/**
 * Created by Mario on 21/10/2015.
 */
public class LoginActivity extends AppCompatActivity {
    private  String  URL = "http://www.meustech.com:8080/medical_new/modelo/db/DBLoginAndroid.php";
    ProgressDialog pDialog;
    ArrayList<NameValuePair> data;
    private EditText editcorreo ;
    private EditText editusuario ;
    private EditText editpassword ;
    private Button loginButton;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0 ;
    private String correo;
    private String usuario ;
    private String password ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editcorreo = (EditText) findViewById(R.id.input_email);
        editusuario = (EditText) findViewById(R.id.input_usuario);
        editpassword = (EditText) findViewById(R.id.input_password);
        loginButton = (Button)findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                correo = editcorreo.getText().toString();
                usuario = editusuario.getText().toString();
                password = editpassword.getText().toString();
            if(!usuario.isEmpty() || !password.isEmpty() ) {
                if (checkLoginData(usuario, password) == true) {
                    new LoginUser().execute();
                    //   Toast.makeText(LoginActivity.this, "Login.....", Toast.LENGTH_SHORT).show();
                } else {
                    err_login();
                }
            }else {
                err_password();
            }
            }
        });
    }
    public boolean checkLoginData(String usuario,String pass){
        if(usuario.equals("")||pass.equals("")){
            Log.e(TAG,"error LoginData");
            return false;
        }else{
            return true;
        }

    }
    public void err_login(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 200, 2,200 };
        final int indexInPatternToRepeat = 1;
        vibrator.vibrate(vibrationPattern,indexInPatternToRepeat);
        Toast toast = Toast.makeText(getApplicationContext(),"Usuario o Password incorrecto",Toast.LENGTH_SHORT);
        toast.show();
    }
    public void err_password(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 200, 2,200 };
        final int indexInPatternToRepeat = 1;
        vibrator.vibrate(vibrationPattern,indexInPatternToRepeat);
        Toast toast = Toast.makeText(getApplicationContext(),"Debe llenar usuario y contraseña",Toast.LENGTH_SHORT);
        toast.show();
    }
    class LoginUser extends AsyncTask<String,String,String>{
        private String iddoctor;
        private String nombreDoctor;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Validando . . .");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            correo = editcorreo.getText().toString();
            usuario = editusuario.getText().toString();
            password = editpassword.getText().toString();
            data =  new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("correo", correo));
            data.add(new BasicNameValuePair("username",usuario));
            data.add(new BasicNameValuePair("password", password));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL, ServiceHandler.POST, data);
            try {
                JSONObject jsonObj = new JSONObject(json);
                JSONArray result = jsonObj.getJSONArray("loginUser");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject loginObj = result.getJSONObject(i);
                    iddoctor = loginObj.getString("iddoctor");
                    nombreDoctor = loginObj.getString("nomdoctor");

                }
                if (iddoctor != "0") {
                    //*acceso al sistema */
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("iddoctor", iddoctor);
                    intent.putExtra("nomdoctor", nombreDoctor);
                    startActivity(intent);
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
                LoginPaciente();
            }
        }
        private void LoginPaciente(){

        }
    }
}
