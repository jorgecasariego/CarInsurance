package atc.android.carinsurance.notifications;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import atc.android.carinsurance.R;
import atc.android.carinsurance.data.PushNotification;

/**
 * Fragmento que despliega una lista de notificaciones
 */
public class NotificationsFragment extends Fragment implements NotificationsContract.View{
    private RecyclerView mRecyclerView;
    private LinearLayout mNoMessagesView;
    private NotificationsAdapter mNotificatiosAdapter;
    private NotificationsPresenter mPresenter;

    public static final String ACTION_NOTIFY_NEW_PROMO = "NOTIFY_NEW_PROMO";
    private BroadcastReceiver mNotificationsReceiver;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance(){
        NotificationsFragment fragment = new NotificationsFragment();

        // Setup de Argumentos
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Gets de argumentos
        }

        mNotificationsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String title = intent.getStringExtra("title");
                String description = intent.getStringExtra("description");
                String expiryDate = intent.getStringExtra("expiry_date");
                String discount = intent.getStringExtra("discount");
                mPresenter.savePushMessage(title, description, expiryDate, discount);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mNotificatiosAdapter = new NotificationsAdapter();
        mRecyclerView = (RecyclerView) root.findViewById(R.id.rv_notifications_list);
        mNoMessagesView = (LinearLayout) root.findViewById(R.id.noMessages);
        mRecyclerView.setAdapter(mNotificatiosAdapter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mNotificationsReceiver, new IntentFilter(ACTION_NOTIFY_NEW_PROMO));
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(getActivity())
                .unregisterReceiver(mNotificationsReceiver);
    }

    @Override
    public void showNotifications(ArrayList<PushNotification> notifications) {
        mNotificatiosAdapter.replaceData(notifications);
    }

    @Override
    public void showEmptyState(boolean empty) {
        mRecyclerView.setVisibility(empty ? View.GONE : View.VISIBLE);
        mNoMessagesView.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void popPushNotification(PushNotification pushMessage) {
        mNotificatiosAdapter.addItem(pushMessage);
    }


    @Override
    public void setPresenter(NotificationsContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = (NotificationsPresenter) presenter;
        } else {
            throw new RuntimeException("El presenter de notificaciones no puede ser null");
        }
    }
}
