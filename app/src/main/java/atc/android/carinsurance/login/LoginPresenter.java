package atc.android.carinsurance.login;

/**
 * Created by jorgecasariego on 26/9/16.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginInteractor.Callback{

    private final LoginContract.View mLoginView;
    private LoginInteractor mLoginInteractor;

    public LoginPresenter(LoginContract.View loginView, LoginInteractor mLoginInteractor) {
        this.mLoginView = loginView;
        loginView.setPresenter(this);
        this.mLoginInteractor = mLoginInteractor;
    }

    @Override
    public void attemptLogin(String email, String password) {
        mLoginView.showProgress(true);
        mLoginInteractor.login(email, password, this);
    }

    @Override
    public void start() {
        // Comprobar si el usuario está logueado
    }

    @Override
    public void onEmailError(String msg) {
        mLoginView.showProgress(false);
        mLoginView.setEmailError(msg);
    }

    @Override
    public void onPasswordError(String msg) {
        mLoginView.showProgress(false);
        mLoginView.setPasswordError(msg);
    }

    @Override
    public void onNetworkConnectFailed() {
        mLoginView.showProgress(false);
        mLoginView.showNetworkError();
    }

    @Override
    public void onBeUserResolvableError(int errorCode) {
        mLoginView.showProgress(false);
        mLoginView.showGooglePlayServiceDialog(errorCode);
    }

    @Override
    public void onGooglePlayServicesFailed() {
        mLoginView.showGooglePlayServiceError();
    }

    @Override
    public void onAuthFailed(String msg) {
        mLoginView.showProgress(false);
        mLoginView.showLoginError(msg);
    }

    @Override
    public void onAuthSuccess() {
        mLoginView.showPushNotifications();
    }
}
