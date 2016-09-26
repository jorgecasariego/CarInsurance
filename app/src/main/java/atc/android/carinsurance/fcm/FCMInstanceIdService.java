package atc.android.carinsurance.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Antes de recibir la notificación, loguearemos el token de registro para tener en cuenta su existencia.
 */
public class FCMInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = FCMInstanceIdService.class.getSimpleName();

    public FCMInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Token: " + fcmToken);

        sendTokenToServer(fcmToken);
    }

    /**
     * El método hipotético llamado sendTokenToServer() enviaría en un futuro el token a nuestro
     * server para guardarlo en la tabla de usuarios en una base de datos personalizada.
     *
     * Esto podría ser de utilidad si deseamos operar con notificaciones para un grupo de dispositivos
     * personalizado. Donde consultariamos los tokens a nuestro servicio web para crear el grupo.
     * @param fcmToken
     */
    private void sendTokenToServer(String fcmToken) {
        // Acciones para enviar token a tu app server
    }
}
