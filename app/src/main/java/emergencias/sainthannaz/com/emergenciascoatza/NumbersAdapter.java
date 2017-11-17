package emergencias.sainthannaz.com.emergenciascoatza;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import emergencias.sainthannaz.com.emergenciascoatza.model.Numbers;
import emergencias.sainthannaz.com.emergenciascoatza.tools.DatabaseHandler;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.MyViewHolder> implements ItemClickListener {
    List<Numbers> numbersList, filterList;
    CustomFilter filter;
    String[] myColors = {"#8b9dc3", "#3b5998", "#001933", "#60566f", "#373442", "#655e6f"};
    private String[] bgColors;
    List<Numbers> cardData = new ArrayList<>();
    public CardView myCard;
    DatabaseHandler db;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, address, description, update, category;
        public ImageView myImageView;
        public ItemClickListener listener;

        public MyViewHolder(View view, ItemClickListener listener) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            category = (TextView) view.findViewById(R.id.category);
            update = (TextView) view.findViewById(R.id.update);
            myImageView = (ImageView) view.findViewById(R.id.cover);
            //myCard = (CardView) view.findViewById(R.id.myCard);
            db = new DatabaseHandler(context);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }

    private Context context;

    public NumbersAdapter(List<Numbers> numbersList, Context context) {
        this.context = context;
        this.numbersList = numbersList;
        this.filterList = numbersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.numbers_list_row_orginal, parent, false);

        return new MyViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DatabaseHandler db = new DatabaseHandler(context);
        ImageView myImageView = (ImageView) holder.myImageView;
        Numbers numbers = numbersList.get(position);
        Log.d("Insert: ", "Inserting ..");
        //db.addCarddata(numbers.getCard_id(), numbers.getCard_title(), numbers.getCard_description(), numbers.getCard_picture(), numbers.getCard_category(), numbers.getCard_update());
        holder.title.setText(numbers.getCard_title());
        holder.description.setText(numbers.getCard_description());
        holder.category.setText(numbers.getCard_category());
        //holder.phone_2.setText(numbers.getCard_phone_2());
        //holder.phone_3.setText(numbers.getCard_phone_3());
        //holder.picture.setText(numbers.getCard_picture());
        String url =  numbers.getCard_picture();
        //holder.map.setText(numbers.getCard_map());
        holder.update.setText(numbers.getCard_update());
        Random rand = new Random();
        int NumberOfAnswers = myColors.length;
        int pick = rand.nextInt(NumberOfAnswers);
        String ColorChoice = myColors[pick];
        System.out.println("get a random color: " + ColorChoice);

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        bgColors = context.getResources().getStringArray(R.array.serial_bg);
        String color = bgColors[position % bgColors.length];
        System.out.println(color);

        //myCard.setCardBackgroundColor(randomAndroidColor);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(Color.parseColor(color))
                .error(Color.parseColor(color))
                .thumbnail(0.1f)
                .centerCrop()
                .into(holder.myImageView);
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
        System.out.println("Se presiono...");
        System.out.println(position);


        int cardId = numbersList.get(position).getCard_id();
        Numbers numbers = db.getInfoMeta(cardId);


        Context context = view.getContext();
        Intent intent = new Intent(context, DetailActivity.class);
        int id = numbers.getCard_id();
        String title = numbers.getCard_title();
        String description = numbers.getCard_description();
        String picture = numbers.getCard_picture();
        String category = numbers.getCard_category();
        String update = numbers.getCard_update();

        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("picture", picture);
        intent.putExtra("category", category);
        intent.putExtra("update", update);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return numbersList.size();
    }

    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }

}

interface ItemClickListener {
    void onItemClick(View view, int position);
}
