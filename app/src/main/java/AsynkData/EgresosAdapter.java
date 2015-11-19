package AsynkData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import dabiaani.com.medical.R;


/**
 * Created by Mario on 19/10/2015.
 */
public class EgresosAdapter extends RecyclerView.Adapter<EgresosAdapter.ViewHolder>{

    private ArrayList<Egresos> datos ;

    @Override
    public long getItemId(int position) {
        return datos.get(position).getId();
    }

    public EgresosAdapter(ArrayList<Egresos> modelRegis){ datos = modelRegis;}



    @Override
    public EgresosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_egresos,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(EgresosAdapter.ViewHolder holder, int position) {
        Egresos reg = datos.get(position);
        holder.getTxt_fecha_egr().setText(reg.getFecha_egreso());
        holder.getTxt_comprobante_egr().setText(reg.getComprobante());
        holder.getTxt_tipoEgreso_egr().setText(reg.getTipo_egreso());
     /*   holder.getTxt_catProveedor_egr().setText(reg.getTipo_proveedor());*/
        holder.getTxt_monto_egr().setText("$ "+ reg.getMonto());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_fecha_egr;
        private TextView txt_comprobante_egr;
        private TextView txt_tipoEgreso_egr;
        private TextView txt_catProveedor_egr;
        private TextView txt_monto_egr;

        public TextView getTxt_fecha_egr() {
            return txt_fecha_egr;
        }

        public void setTxt_fecha_egr(TextView txt_fecha_egr) {
            this.txt_fecha_egr = txt_fecha_egr;
        }

        public TextView getTxt_comprobante_egr() {
            return txt_comprobante_egr;
        }

        public void setTxt_comprobante_egr(TextView txt_comprobante_egr) {
            this.txt_comprobante_egr = txt_comprobante_egr;
        }

        public TextView getTxt_tipoEgreso_egr() {
            return txt_tipoEgreso_egr;
        }

        public void setTxt_tipoEgreso_egr(TextView txt_tipoEgreso_egr) {
            this.txt_tipoEgreso_egr = txt_tipoEgreso_egr;
        }

        public TextView getTxt_catProveedor_egr() {
            return txt_catProveedor_egr;
        }

        public void setTxt_catProveedor_egr(TextView txt_catProveedor_egr) {
            this.txt_catProveedor_egr = txt_catProveedor_egr;
        }

        public TextView getTxt_monto_egr() {
            return txt_monto_egr;
        }

        public void setTxt_monto_egr(TextView txt_monto_egr) {
            this.txt_monto_egr = txt_monto_egr;
        }
//</editor-fold>

        public ViewHolder(View itemView) {
            super(itemView);
                txt_fecha_egr = (TextView) itemView.findViewById(R.id.tv_fecha);
                txt_comprobante_egr = (TextView) itemView.findViewById(R.id.tv_comprobante);
                txt_tipoEgreso_egr = (TextView) itemView.findViewById(R.id.tv_tipoEgreso);
               /* txt_catProveedor_egr = (TextView) itemView.findViewById(R.id.tv_catProveedor);*/
                txt_monto_egr= (TextView) itemView.findViewById(R.id.tv_monto);
        }
    }
}
