package AsynkData;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.TestListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dabiaani.com.medical.Consulta;
import dabiaani.com.medical.ConsultaHistorial;
import dabiaani.com.medical.PacienteAddNumero;
import dabiaani.com.medical.PacienteDetalle;
import dabiaani.com.medical.PacienteDialogCall;
import dabiaani.com.medical.R;
import dabiaani.com.medical.Tab2;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Mario on 07/10/2015.
 */
public class PacientesAdapter extends RecyclerView.Adapter<PacientesAdapter.ViewHolder> {

    private ArrayList<Pacientes> data;
    private OnItemClickListener mItemClickListener;
    private  Pacientes registros;
    private FragmentActivity context;

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public PacientesAdapter(ArrayList<Pacientes> modelData, FragmentActivity context) {
        data = modelData;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }
    @Override
    public PacientesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_paciente, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(PacientesAdapter.ViewHolder holder, final int position) {
        registros = data.get(position);
        holder.getTxt_nombre().setText(registros.getNombre() + " " + registros.getApellidos());
        holder.getTxt_tel().setText("Tel : "+registros.getTel());
        holder.getTxt_cel().setText("Cel : " + registros.getCel());


        final String telNumero =     data.get(position).getTel();
        final String telCelular =     data.get(position).getCel();
        final String paciente  = String.valueOf(data.get(position).getId());
        holder.getBtn_historial().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ConsultaHistorial.class);

                intent.putExtra("idpaciente",paciente);
                context.startActivity(intent);

            }
        });

        holder.getBtn_llamar().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
               call(telNumero,telCelular,paciente);
            }
        });

        holder.getBtn_consulta().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent in=new Intent(context,Consulta.class);
                try{
                    Intent intent = new Intent(context, Consulta.class);
                    String paciente = String.valueOf(data.get(position).getId());
                    String nompaciente = String.valueOf(data.get(position).getNombre())+String.valueOf(data.get(position).getApellidos());
                    String edadpaciente  = String.valueOf(data.get(position).getFecha_nac());
                    String sangrepaciente = String.valueOf(data.get(position).getTipo_sangre());
                    String hiperpaciente = String.valueOf(data.get(position).getHipertencion());
                    String diabetespaciente = String.valueOf(data.get(position).getDiabetes());
                    intent.putExtra("idpaciente",paciente);
                    intent.putExtra("nompaciente",nompaciente);
                    intent.putExtra("edadpaciente",edadpaciente);
                    intent.putExtra("sangrepaciente",sangrepaciente);
                    intent.putExtra("hiperpaciente",hiperpaciente);
                    intent.putExtra("diabetespaciente",diabetespaciente);
                    context.startActivity(intent);
                    //Toast.makeText(context,"paciente"+data.get(position).getId(),Toast.LENGTH_SHORT).show();

                } catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(context,"No se pudo realizar esta accion, intente de nuevo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void call(String local,String celular,String paciente ) {
        if(local.isEmpty() && celular.isEmpty()){
            Bundle a  = new Bundle();
            FragmentManager dialogf = context.getFragmentManager();
            PacienteAddNumero dialog = new PacienteAddNumero();
            a.putString("idpaciente",""+paciente);
            dialog.setArguments(a);
            dialog.show(dialogf, "dialog number");

        }else{
            FragmentManager manager = context.getFragmentManager();
            int position = 0;
            Bundle b  = new Bundle();
            /** Instantiating the DialogFragment class */
            PacienteDialogCall alert = new PacienteDialogCall();
            /** Creating a bundle object to store the selected item's index */
            /** Storing the selected item's index in the bundle object */
            b.putInt("position", position);
            b.putString("local", local);
            b.putString("celular", celular);
            alert.setArguments(b);
            alert.show(manager, "alert_dialog_radio");

        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


      public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txt_nombre;
        private TextView txt_tel;
        private TextView txt_cel;
        private ImageButton btn_llamar;
        private ImageButton btn_consulta;
        private ImageButton btn_historial;
        public ImageButton getBtn_llamar() {
            return btn_llamar;}
        public ImageButton getBtn_consulta() {
            return btn_consulta;
        }
        public ImageButton getBtn_historial() {
            return btn_historial;
        }

        public TextView getTxt_nombre() {
            return txt_nombre;
        }
        public TextView getTxt_tel() {
            return txt_tel;
        }
        public TextView getTxt_cel() {
            return txt_cel;
        }



        public ViewHolder(View itemView) {
            super(itemView);
            txt_nombre = (TextView) itemView.findViewById(R.id.tv_nombre);
            txt_tel = (TextView) itemView.findViewById(R.id.tv_tel);
            txt_cel = (TextView) itemView.findViewById(R.id.tv_cel);
            btn_llamar = (ImageButton) itemView.findViewById(R.id.addLLamar);
            btn_consulta = (ImageButton) itemView.findViewById(R.id.addConsulta);
            btn_historial = (ImageButton) itemView.findViewById(R.id.addListCon);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, (int) getItemId());
            }
            return;
        }




    }

}
