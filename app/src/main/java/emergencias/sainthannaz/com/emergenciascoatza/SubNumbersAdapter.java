package emergencias.sainthannaz.com.emergenciascoatza;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import emergencias.sainthannaz.com.emergenciascoatza.model.SubNumbers;
import emergencias.sainthannaz.com.emergenciascoatza.tools.DatabaseHandler;

/**
 * Created by Gabriel on 10/11/2017.
 */

public class SubNumbersAdapter  extends RecyclerView.Adapter<SubNumbersAdapter.MyViewHolder> implements ItemClickListener{
    List<SubNumbers> SubNumbersList;

    @Override
    public void onItemClick(View view, int position) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView phone, address, map, update;
        public ImageView phoneImg;
        public ItemClickListener listener;

        public MyViewHolder(View view, ItemClickListener listener) {
            super(view);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            update = (TextView) view.findViewById(R.id.update);
            phoneImg = (ImageView) view.findViewById(R.id.cover);
            //map = (TextView) view.findViewById(R.id.map);

            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }

    private Context context;

    public SubNumbersAdapter(List<SubNumbers> SubNumbersList, Context context) {
        this.context = context;
        this.SubNumbersList = SubNumbersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.numbers_list_row_subdata, parent, false);
        return new MyViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DatabaseHandler db = new DatabaseHandler(context);

        SubNumbers SubNumbers = SubNumbersList.get(position);
        Log.d("Insert: ", "Inserting ..");

        holder.address.setText(SubNumbers.getCard_address());
        holder.phone.setText(SubNumbers.getCard_phone());
        holder.phoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Se llamara...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return SubNumbersList.size();
    }


}
