package atc.android.carinsurance.notifications;

import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import atc.android.carinsurance.data.PushNotification;
import atc.android.carinsurance.data.PushNotificationsRepository;

/**
 * Created by jorgecasariego on 26/9/16.
 *
 * Presentador para cargar una lista de notificaciones
 *
 * Los siguientes son comportamientos que debe tener la vista de las notificaciones al iniciarse la screen:
 *
 *  - Mostrar notificaciones
 *  - Mostrar mensaje al no haber notificaciones
 *
 *  En cuanto al presentador debería:
 *
 *  - Cargar las notificaciones de la fuente de datos
 *  - Registrar el cliente FCM
 *
 *
 */

public class NotificationsPresenter implements NotificationsContract.Presenter {

    private final NotificationsContract.View mNotificationView;
    private final FirebaseMessaging mFCMInteractor;


    public NotificationsPresenter(NotificationsContract.View notificationView,
                                      FirebaseMessaging FCMInteractor) {
        mNotificationView = notificationView;
        mFCMInteractor = FCMInteractor;

        notificationView.setPresenter(this);
    }

    @Override
    public void registerAppClient() {
        mFCMInteractor.subscribeToTopic("promos");
    }

    @Override
    public void loadNotifications() {

        // LLamamos el método getNofications() del repositorio de notificaciones.
        PushNotificationsRepository.getInstance().getPushNotifications(
                new PushNotificationsRepository.LoadCallback() {
                    @Override
                    public void onLoaded(ArrayList<PushNotification> notifications) {
                        if (notifications.size() > 0) {
                            mNotificationView.showEmptyState(false);
                            mNotificationView.showNotifications(notifications);
                        } else {
                            mNotificationView.showEmptyState(true);
                        }
                    }
                }
        );
    }

    @Override
    public void savePushMessage(String title, String description, String expiryDate, String discount) {
        PushNotification pushMessage = new PushNotification();
        pushMessage.setmTitle(title);
        pushMessage.setmDescription(description);
        pushMessage.setmExpiryDate(expiryDate);
        pushMessage.setmDiscount(TextUtils.isEmpty(discount) ? 0 : Float.parseFloat(discount));

        PushNotificationsRepository.getInstance().savePushNotification(pushMessage);

        mNotificationView.showEmptyState(false);
        mNotificationView.popPushNotification(pushMessage);
    }

    @Override
    public void start() {
        registerAppClient();
        loadNotifications();
    }
}
