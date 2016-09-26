package atc.android.carinsurance.data;

import java.util.UUID;

/**
 * Created by jorgecasariego on 26/9/16.
 *
 * POJO java para representar la entidad de las notificaciones
 */

public class PushNotification {
    private String id;
    private String mTitle;
    private String mDescription;
    private String mExpiryDate;
    private float mDiscount;

    public PushNotification() {
        id = UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmExpiryDate(String mExpiryDate) {
        this.mExpiryDate = mExpiryDate;
    }

    public void setmDiscount(float mDiscount) {
        this.mDiscount = mDiscount;
    }

    public String getId() {
        return id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmExpiryDate() {
        return mExpiryDate;
    }

    public float getmDiscount() {
        return mDiscount;
    }
}
