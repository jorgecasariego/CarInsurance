package atc.android.carinsurance.login;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import atc.android.carinsurance.R;

/**
 * Actividad que actúa como controlador del login
 *
 *  1. La vista recibe comunica al presentador cuando el usuario presiono el botón de login.
 *  2. El presentador le ordena al interactor que inicie el proceso de autenticación.
 *  3. El interactor hace una llamado al Firebase Authentication SDK para autenticar en el server.
 *  4. Firebase Authentication inicia un proceso asíncrono para enviar una petición al server y se
 *      queda esperando por la respuesta.
 *  5. Al llegar la respuesta se le transmite al interactor.
 *  6. El interactor notifica al presenter si el login fue exitoso o hubo algún fallo.
 *  7. El presenter actualiza la vista ya sea para alertar al usuario de los errores o para dar paso
 *      a la actividad de notificaciones.
 */
public class LoginActivity extends AppCompatActivity implements LoginFragment.Callback{
    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupTranslucentStatusBar();

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.login_container);

        if(loginFragment == null){
            loginFragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.login_container, loginFragment)
                    .commit();
        }

        LoginInteractor loginInteractor = new LoginInteractor(getApplicationContext(), FirebaseAuth.getInstance());
        mPresenter = new LoginPresenter(loginFragment, loginInteractor);
    }

    private void setupTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onInvokeGooglePlayServices(int codeError) {
        showPlayServicesErrorDialog(codeError);
    }

    private void showPlayServicesErrorDialog(final int codeError) {
        Dialog dialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(
                        LoginActivity.this,
                        codeError,
                        REQUEST_GOOGLE_PLAY_SERVICES
                );

        dialog.show();
    }
}
