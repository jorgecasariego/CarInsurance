package atc.android.carinsurance.notifications;

import java.util.ArrayList;

import atc.android.carinsurance.data.PushNotification;
import atc.android.carinsurance.interfaces.BasePresenter;
import atc.android.carinsurance.interfaces.BaseView;

/**
 * Created by jorgecasariego on 26/9/16.
 *
 * Interfaz para la representación general de la vista y el presentador
 */

public class NotificationsContract {

    interface View extends BaseView<Presenter> {

        void showNotifications(ArrayList<PushNotification> notifications);

        void showEmptyState(boolean empty);

        void popPushNotification(PushNotification pushMessage);
    }

    interface Presenter extends BasePresenter {

        void registerAppClient();

        void loadNotifications();

        void savePushMessage(String title, String description,
                             String expiryDate, String discount);
    }
}
