package emergencias.sainthannaz.com.emergenciascoatza;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emergencias.sainthannaz.com.emergenciascoatza.model.SubNumbers;
import emergencias.sainthannaz.com.emergenciascoatza.tools.DatabaseHandler;

import static emergencias.sainthannaz.com.emergenciascoatza.TabFragment.viewPager;


/**
 * Created by Gabriel on 10/11/2017.
 */

public class SubNumbersAdapter extends RecyclerView.Adapter<SubNumbersAdapter.MyViewHolder> implements ItemClickListener {
    List<SubNumbers> SubNumbersList;
    FragmentManager FM;
    FragmentTransaction FT;
    DetailActivity findMe;
    @Override
    public void onItemClick(View view, int position) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView phone, address, map, update;
        public CircleImageView phoneImg;
        public Button searchInMap;
        public ItemClickListener listener;

//        public ViewPager viewPager;

        public MyViewHolder(View view, ItemClickListener listener) {
            super(view);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            update = (TextView) view.findViewById(R.id.update);
            phoneImg = (CircleImageView) view.findViewById(R.id.cover);
            searchInMap = (Button) view.findViewById(R.id.searchInMap);

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

        final SubNumbers SubNumbers = SubNumbersList.get(position);
        Log.d("Insert: ", "Inserting ..");

        holder.address.setText(SubNumbers.getCard_address());
        holder.phone.setText(SubNumbers.getCard_phone());
        System.out.println(SubNumbers.getCard_map());
        holder.phoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SubNumbers.getCard_phone() != null && !SubNumbers.getCard_phone().isEmpty()) {
                    Toast.makeText(context, "Se llamara a " + SubNumbers.getCard_phone(), Toast.LENGTH_SHORT).show();
                    call(SubNumbers.getCard_phone());
                } else {
                    Toast.makeText(context, "No cuenta con número!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.searchInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(SubNumbers.getCard_map());
                String mapToFind = SubNumbers.getCard_map();
                findMe.findInMapDetail(mapToFind);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SubNumbersList.size();
    }

    private void call(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.putExtra("flag", 1);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(callIntent);
    }

}
