package emergencias.sainthannaz.com.emergenciascoatza;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static emergencias.sainthannaz.com.emergenciascoatza.TabFragment.int_items;


/**
 * Created by Admin on 3/1/2017.
 */

public class MyAdapter  extends FragmentPagerAdapter {
    public static String tab1_title;
    public static String tab2_title;

    public MyAdapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ResumeFragment();
            case 1:
                return new MapFragment();
        }
        return null;
    }

    @Override
    public int getCount() {


        return int_items;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:{
                return "Números";
            }
            case 1: {
                return "Mapa";
            }
        }

        return null;
    }
}
