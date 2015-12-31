package me.pepyakin.branchweirdstuff;

import android.graphics.Typeface;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author pepyakin
 */
final class TextViewLogger {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);

    private final TextView mContentTextView;
    private Truss mLoggingBuffer;

    TextViewLogger(TextView contentTextView) {
        mContentTextView = contentTextView;
        mLoggingBuffer = new Truss();
    }

    public void log(String message) {
        String timeStampString = DATE_FORMAT.format(new Date());

        mLoggingBuffer.pushSpan(new StyleSpan(Typeface.BOLD));
        mLoggingBuffer.append(timeStampString);
        mLoggingBuffer.popSpan();

        mLoggingBuffer.append('\n');
        mLoggingBuffer.append(message);
        mLoggingBuffer.append("\n\n");

        mContentTextView.setText(mLoggingBuffer.build());
    }

    public void clear() {
        mLoggingBuffer = new Truss();
        mContentTextView.setText("");
    }
}
