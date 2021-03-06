package me.pepyakin.branchweirdstuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextViewLogger mTextViewLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView contentTextView = (TextView) findViewById(R.id.main_content_textview);
        mTextViewLogger = new TextViewLogger(contentTextView);

        mTextViewLogger.log("onCreate(savedInstanceState=" + savedInstanceState + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem clearMenuAction = menu.add("Clear");
        clearMenuAction.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        clearMenuAction.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mTextViewLogger.clear();
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        mTextViewLogger.log("onNewIntent(intent=" + intent + ")");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextViewLogger.log("onStart");

        /*
         * Ok, so how can you work around this? I just tested this and it works fine. Please add:
         * this.getIntent().setData(null);
         * before you call initSession in onStart. This will prevent stale data from ever passing
         * into initSession if onStart is called multiple times without the intent being reset from onNewIntent.
         */
        getIntent().setData(null);

        Branch.getInstance().initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                String diagnosticMessageContent =
                        "referringParams="
                        + referringParams.toString()
                        + ", error="
                        + error;
                mTextViewLogger.log(diagnosticMessageContent);
            }
        }, getIntent().getData() /* so it will be always null? */, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTextViewLogger.log("onStop");
    }
}
