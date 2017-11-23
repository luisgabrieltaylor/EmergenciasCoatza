package emergencias.sainthannaz.com.emergenciascoatza;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import emergencias.sainthannaz.com.emergenciascoatza.app.MyApplication;
import emergencias.sainthannaz.com.emergenciascoatza.model.Numbers;
import emergencias.sainthannaz.com.emergenciascoatza.tools.ConnectivityStatus;
import emergencias.sainthannaz.com.emergenciascoatza.tools.DatabaseHandler;
import emergencias.sainthannaz.com.emergenciascoatza.tools.Hosts;
import emergencias.sainthannaz.com.emergenciascoatza.tools.PreferanceManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment {

    private List<Numbers> numbersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NumbersAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private String phoneNumber;
    private Gson gson = new Gson();
    DatabaseHandler db;
    private static final String TAG = MainActivity.class.getSimpleName();
    public PreferanceManager prefManager;
    private AdView mAdView;
    public ResumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_resume,container,false);
        setHasOptionsMenu(true);
        db = new DatabaseHandler(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        MobileAds.initialize(getContext(), "ca-app-pub-7418223157292725~5198435692");
        mAdapter = new NumbersAdapter(numbersList, getContext());
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("B2AA3E70A677C5F4956FF6A5601D3744")
                .build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }
            @Override
            public void onAdClosed() {
                Toast.makeText(getActivity(), "La publicidad se a cerrado!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getActivity(), "Fallo al cargar la publicidad" + errorCode, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getActivity(), "¡Gracias por contribuir!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prefManager = new PreferanceManager(getContext());
        if (!prefManager.isFirstTimeLaunch()) {
            System.out.println(prefManager);
            System.out.println("No es la primera vez");
            getContext().registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        } else if (prefManager.isFirstTimeLaunch()){
            insertSQLiteData();
            getContext().registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            System.out.println("Es la primera vez");
            System.out.println(prefManager);
        }

        //recyclerView.setAdapter(mAdapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4
        );

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //cargarAdaptador();
                        getContext().registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
                        showSnackDataRefresh();
                        refreshLayout.setRefreshing(false);
                    }
                }
        );
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Left", "onCreate()");

    }

    public void insertSQLiteData(){
        //db.DeleteData();
        Log.d("Insert manual data: ", "Inserting manual data..");
        System.out.println("Se carga información por primera vez");
        db.addCarddata(1, "Tarjeta 1", "Esta es la dirección a mostrar", "", "Seguridad", "2017/11/08");
        db.addCarddata(2, "Tarjeta 2", "Esta es la dirección a mostrar", "","Salud","2017/11/08");
        db.addCarddata(3, "Tarjeta 3", "Esta es la dirección a mostrar", "","Salud","2017/11/08");
        db.addCarddata(4, "Tarjeta 4", "Esta es la dirección a mostrar", "","Vehiculos","2017/11/08");
        db.addCarddata(5, "Tarjeta 5", "Esta es la dirección a mostrar", "","Seguridad, Vigilancia","2017/11/08");

        db.addSubdata(1, 1, "Direccion correspondiente a 1", "9211111111", "coordenadas 1.1", "2017/11/08");
        db.addSubdata(2, 1, "Direccion correspondiente a 1", "9211111111", "coordenadas 1.2", "2017/11/08");
        db.addSubdata(3, 2, "Direccion correspondiente a 2", "9222222222", "coordenadas 2", "2017/11/08");

        List<Numbers> numbers = db.getAllContacts();
        mAdapter = new NumbersAdapter(numbers, getActivity());
        recyclerView.setAdapter(mAdapter);
        prefManager.setFirstTimeLaunch(false);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!ConnectivityStatus.isConnected(getActivity())){
                System.out.println("No hay internet");
                List<Numbers> numbers = db.getAllContacts();
                mAdapter = new NumbersAdapter(numbers, getContext());
                recyclerView.setAdapter(mAdapter);
                // no connection
            }else {
                // connected
                System.out.println("Hay internet");
                cargarAdaptador();
            }
        }
    };

    public void cargarAdaptador() {
        // Petición GET
        System.out.println("Se borro la BD, se procede a la descarga de información...");
        CustomVolleyRequest.
                getInstance(getContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Hosts.GET,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        System.out.println(response);
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }
                        )
                );
    }

    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    System.out.println("Petición exitosa...");
                    showSnackDataload();
                    db.DeleteData();
                    db.DeleteSubData();
                    // Obtener array "metas" Json
                    JSONArray metas = response.getJSONArray("metas");

                    for (int i = 0; i < metas.length(); i++) {
                        JSONObject item = metas.getJSONObject(i);
                        String card__id =  item.getString("card_id");
                        int card_id = Integer.parseInt(card__id);
                        String card_title = item.getString("card_title");
                        String card_description = item.getString("card_description");
                        String card_picture = item.getString("card_picture");
                        String card_category = item.getString("card_category");
                        String card_update = item.getString("card_update");

                        db.addCarddata(card_id, card_title, card_description, card_picture, card_category, card_update);
                        System.out.println(Integer.toString(card_id));
                        System.out.println(card_title);
                        System.out.println(card_description);
                        System.out.println(card_picture);
                        System.out.println(card_category);
                        System.out.println(card_update);
                        System.out.println("----------------------------------------------");
                    }

                    JSONArray submetas = response.getJSONArray("submetas");
                    for (int j = 0; j < submetas.length(); j++) {
                        JSONObject item = submetas.getJSONObject(j);
                        String phone_card__id =  item.getString("phone_card_id");
                        int phone_card_id = Integer.parseInt(phone_card__id);
                        String card__id =  item.getString("card_id");
                        int card_id = Integer.parseInt(card__id);
                        String card_address = item.getString("card_address");
                        String card_phone = item.getString("card_phone");
                        String card_map = item.getString("card_map");
                        String card_update = item.getString("card_update");

                        db.addSubdata(phone_card_id, card_id, card_address, card_phone, card_map, card_update);
                        System.out.println(Integer.toString(phone_card_id));
                        System.out.println(Integer.toString(card_id));
                        System.out.println(card_address);
                        System.out.println(card_phone);
                        System.out.println(card_map);
                        System.out.println(card_update);
                        System.out.println("----------------------------------------------");
                    }

                    // Parsear con Gson
                    Numbers[] card = gson.fromJson(metas.toString(), Numbers[].class);
                    //System.out.println(card);
                    // Inicializar adaptador
                    mAdapter = new NumbersAdapter(Arrays.asList(card), getContext());
                    // Setear adaptador a la lista
                    recyclerView.setAdapter(mAdapter);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(getContext(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
                //pubAdapter.filter(newText);

            }
        });
    }

    public void showSnackDataload(){
        /*
        Snackbar snackbar = Snackbar
                .make(getView(), "Message is deleted", Snackbar.LENGTH_LONG);
        snackbar.show();
        */
        Snackbar snackbar = Snackbar.make(getView(), R.string.notice, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView textView = (TextView)snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lightbulb, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
        snackbar.show();

        /*
        String message;
        message = "";
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();*/
    }

    public void showSnackDataRefresh(){
        Snackbar snackbar = Snackbar.make(getView(), R.string.refresh, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView textView = (TextView)snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
        snackbar.show();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().trackEvent("Pantalla","Ejecucion","Tab principal");
        MyApplication.getInstance().trackScreenView("ResumeFragment");
        if (mAdView != null) {
            mAdView.resume();
        }
    }

}
