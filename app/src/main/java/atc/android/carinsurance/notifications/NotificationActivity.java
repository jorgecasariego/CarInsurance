package atc.android.carinsurance.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import atc.android.carinsurance.R;
import atc.android.carinsurance.login.LoginActivity;

/**
 * Actividad que actúa como screen de las notificaciones
 *
 *  Analicemos el flujo:
 *
 *  1. Usamos la consola de Firebase en la sección Notifications para crear y enviar un mensaje al servidor
 *  2. El Push Messaging Service lo recibe y lo despacha hacia la app Android, donde el Firebase Cloud Messaging
 *     internamente obtendrá el mensaje.
 *  3. Desde el FCM SDK envíamos un broadcast hacia la vista, donde estará esperando un BroadcastReceiver.
 *  4. Las vista comunica al presenter que llegó una nueva push notification
 *  5. El presenter se comunica con el repositorio de datos para salvar la instancia temporalmente.
 *  6. Una vez el repositorio haya guardado satisfactoriamente la entidad, se lo comunica al presenter.
 *  7. Con ello el presenter actualiza la lista en la vista, apilando el nuevo ítem.
 *
 */
public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = NotificationActivity.class.getSimpleName();

    private NotificationsFragment mNotificationsFragment;
    private NotificationsPresenter mNotificationsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_notifications));

        // ¿Existe un usuario logueado?
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        mNotificationsFragment =
                (NotificationsFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.notifications_container);

        if (mNotificationsFragment == null) {
            mNotificationsFragment = NotificationsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.notifications_container, mNotificationsFragment)
                    .commit();
        }

        mNotificationsPresenter = new NotificationsPresenter(
                mNotificationsFragment, FirebaseMessaging.getInstance());
    }
}
