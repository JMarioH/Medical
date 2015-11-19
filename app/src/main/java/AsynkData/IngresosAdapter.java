package AsynkData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import org.w3c.dom.Text;
import dabiaani.com.medical.R;
/**
 * Created by Mario on 15/10/2015.
 */
public class IngresosAdapter extends RecyclerView.Adapter<IngresosAdapter.ViewHolder>{
    private ArrayList<Ingresos> datos;

    @Override
    public long getItemId(int position) {
        return datos.get(position).getId();
    }

    public IngresosAdapter(ArrayList<Ingresos> modelData){
        datos = modelData;
    }



    @Override
    public IngresosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_ingresos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IngresosAdapter.ViewHolder holder, int position) {
        Ingresos reg = datos.get(position);

        holder.getTxt_fecha().setText(reg.getFecha());
        holder.getTxt_tipoIngreso().setText(reg.getTipo_ingreso());
        holder.getTxt_monto().setText(" $"+reg.getMonto());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_fecha;
        private TextView txt_monto;
        private TextView txt_tipoIngreso;

        public TextView getTxt_fecha() {
            return txt_fecha;
        }

        public void setTxt_fecha(TextView txt_fecha) {
            this.txt_fecha = txt_fecha;
        }

        public TextView getTxt_monto() {
            return txt_monto;
        }

        public void setTxt_monto(TextView txt_monto) {
            this.txt_monto = txt_monto;
        }

        public TextView getTxt_tipoIngreso() {
            return txt_tipoIngreso;
        }

        public void setTxt_tipoIngreso(TextView txt_tipoIngreso) {
            this.txt_tipoIngreso = txt_tipoIngreso;
        }

        public ViewHolder(View itemView){
            super(itemView);

            txt_fecha = (TextView) itemView.findViewById(R.id.tv_fecha);
            txt_monto = (TextView) itemView.findViewById(R.id.tv_monto);
            txt_tipoIngreso = (TextView) itemView.findViewById(R.id.tv_tipoIngreso);
        }

    }
}
