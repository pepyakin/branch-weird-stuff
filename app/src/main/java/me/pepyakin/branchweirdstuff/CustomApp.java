package me.pepyakin.branchweirdstuff;

import android.app.Application;
import io.branch.referral.Branch;

/**
 * @author pepyakin
 */
public final class CustomApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Branch.getAutoInstance(this);
    }
}
