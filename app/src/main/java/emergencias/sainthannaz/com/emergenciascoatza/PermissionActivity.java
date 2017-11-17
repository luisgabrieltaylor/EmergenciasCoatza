package emergencias.sainthannaz.com.emergenciascoatza;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.greysonparrelli.permiso.Permiso;
import com.greysonparrelli.permiso.PermisoActivity;

import emergencias.sainthannaz.com.emergenciascoatza.tools.PreferanceManager;

public class PermissionActivity extends PermisoActivity {

    private static final String TAG = "";
    //Our button
    private Button btnRequest;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public PreferanceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window. FEATURE_NO_TITLE); // Permite la visualizacion de la aplicacion sin ventana de titulo

        setContentView(R.layout.activity_permission);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PreferanceManager(getApplication());
        if (!prefManager.isFirstTimeLaunch()) {
            launchMainScreen();
            finish();
        }

        btnRequest = (Button) findViewById(R.id.buttonRequestPermission);
        // Checking for first time launch - before calling setContentView()
        System.out.println(prefManager);

        //Initializing button
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
                prefManager.setFirstTimeLaunch(false);
            }
        });
        // carry on the normal flow, as the case of  permissions  granted.

    }

    private void checkAndRequestPermissions() {
        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.isPermissionGranted(Manifest.permission.INTERNET)) {
                    // Internet permission granted!
                }
                if (resultSet.isPermissionGranted(Manifest.permission.CALL_PHONE)) {
                    //
                }
                if (resultSet.isPermissionGranted(Manifest.permission.READ_PHONE_STATE)) {
                    //
                }
                if (resultSet.isPermissionGranted(Manifest.permission.ACCESS_NETWORK_STATE)) {
                    //
                }
                if (resultSet.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //
                }
                if (resultSet.isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //
                }
                finish();
                startActivity(new Intent(PermissionActivity.this, MainActivity.class));
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                Permiso.getInstance().showRationaleInDialog("Title", "Message", null, callback);
            }

        }, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private void launchMainScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(PermissionActivity.this, MainActivity.class));
        finish();
    }
}