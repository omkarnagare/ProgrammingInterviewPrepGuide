package com.nagare.balkrishna.omkar.programminginterviewprepguide.Application;

/**
 * Created by OMKARNAGARE on 6/4/2017.
 */

import android.content.Context;

public class CustomClipboardManager {

    @SuppressWarnings("deprecation")
    public static boolean copyToClipboard( String text) {

        Context context = ProgrammingInterviewPrepGuideApp.getAppContext();

        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(
                                "", text);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
