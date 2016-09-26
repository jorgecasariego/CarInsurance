package atc.android.carinsurance.login;

import atc.android.carinsurance.interfaces.BasePresenter;
import atc.android.carinsurance.interfaces.BaseView;

/**
 * Created by jorgecasariego on 26/9/16.
 *
 * Define la capa de vista y la interacción con el presentador
 *
 * Interacción MVP en Login
 *
 * BaseView y BasePresenter son interfaces con el comportamiento general que esperamos de todas
 * las vistas y presentadores de la app
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showProgress(boolean show);

        void setEmailError(String error);

        void setPasswordError(String error);

        void showLoginError(String msj);

        void showPushNotifications();

        void showGooglePlayServiceDialog(int errorCode);

        void showGooglePlayServiceError();

        void showNetworkError();
    }

    interface Presenter extends BasePresenter {
        void attemptLogin(String email, String password);
    }
}
