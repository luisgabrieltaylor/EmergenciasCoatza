package emergencias.sainthannaz.com.emergenciascoatza;

import android.widget.Filter;


import java.util.ArrayList;
import java.util.List;

import emergencias.sainthannaz.com.emergenciascoatza.model.Numbers;


/**
 * Created by Gabriel on 17/02/2017.
 */

public class CustomFilter extends Filter {

    NumbersAdapter adapter;
    List<Numbers> filterList;


    public CustomFilter(List<Numbers> filterList, NumbersAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<Numbers> filteredNumbers =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //Title
                if(filterList.get(i).getCard_title().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredNumbers.add(filterList.get(i));
                }

                //Category
                if(filterList.get(i).getCard_category().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredNumbers.add(filterList.get(i));
                }
            }

            results.count= filteredNumbers.size();
            results.values= filteredNumbers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.numbersList= (List<Numbers>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}