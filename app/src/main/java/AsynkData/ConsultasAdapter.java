package AsynkData;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dabiaani.com.medical.R;

/**
 * Created by Mario on 13/11/2015.
 */
public class ConsultasAdapter extends RecyclerView.Adapter<ConsultasAdapter.ViewHolder>{

        private ArrayList<Consultas> data;
        private Consultas registros ;
        private OnItemClickListener mItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public ConsultasAdapter(ArrayList<Consultas> modeldata) {
        data = modeldata;
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public ConsultasAdapter.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_historial_consulta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ConsultasAdapter.ViewHolder holder, int position) {
        registros = data.get(position);
        holder.getTxt_fecha().setText(registros.getFecha());
        holder.getTxt_dolencia().setText(registros.getDolecia());
        holder.getTxt_diagnostico().setText(registros.getDiagnostico());
        holder.getTxt_presion().setText(registros.getPresion());
        holder.getTxt_peso().setText(registros.getPeso());
        holder.getTxt_ritmo().setText(registros.getRitmo_c());
        holder.getTxt_estatura().setText(registros.getEstatura());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txt_fecha;
        private TextView txt_diagnostico;
        private TextView txt_dolencia;
        private TextView txt_peso;
        private TextView txt_presion;
        private TextView txt_ritmo;
        private TextView txt_estatura;

        public TextView getTxt_peso() {
            return txt_peso;
        }

        public TextView getTxt_presion() {
            return txt_presion;
        }

        public TextView getTxt_ritmo() {
            return txt_ritmo;
        }

        public TextView getTxt_estatura() {
            return txt_estatura;
        }

        public TextView getTxt_fecha() {
            return txt_fecha;
        }
        public TextView getTxt_diagnostico() {
            return txt_diagnostico;
        }
        public TextView getTxt_dolencia() {
            return txt_dolencia;
        }



        public ViewHolder(View itemView) {
            super(itemView);
            txt_fecha = (TextView) itemView.findViewById(R.id.txt_fechaConsulta);
            txt_diagnostico = (TextView) itemView.findViewById(R.id.txt_diagnosticoConsulta);
            txt_dolencia = (TextView) itemView.findViewById(R.id.txt_dolenciaConsulta);
            txt_presion = (TextView) itemView.findViewById(R.id.txt_presionConsulta);
            txt_peso = (TextView) itemView.findViewById(R.id.txt_pesoCosulta);
            txt_ritmo = (TextView) itemView.findViewById(R.id.txt_ritmoCosulta);
            txt_estatura = (TextView) itemView.findViewById(R.id.txt_estauraConsulta);

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
