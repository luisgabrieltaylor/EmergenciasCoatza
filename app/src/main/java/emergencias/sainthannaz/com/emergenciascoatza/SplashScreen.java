package emergencias.sainthannaz.com.emergenciascoatza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import emergencias.sainthannaz.com.emergenciascoatza.app.AnalyticsTrackers;
import emergencias.sainthannaz.com.emergenciascoatza.app.MyApplication;

/**
 * Created by Gabriel on 09/11/2017.
 */

public class SplashScreen extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 500;
    private static String TAG = MainActivity.class.getSimpleName();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Permite la visualizacion de la aplicacion sin ventana de titulo
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,// Permite la visualizacion de la aplicacion sin ventana de titulo
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Permite la visualizacion de la aplicacion sin ventana de titulo
        setContentView(R.layout.activity_splash_screen);
        MyApplication.getInstance().trackScreenView("Splash Screen");
        //ImageView logo = (ImageView) findViewById(R.id.logo);
        //logo.setVisibility(ImageView.VISIBLE);

        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(200);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    startActivity(new Intent(SplashScreen.this, PermissionActivity.class));
                    onStop();
                }
            }
        };
        splashTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }

}
