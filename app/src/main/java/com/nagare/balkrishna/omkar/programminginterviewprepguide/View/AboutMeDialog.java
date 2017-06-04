package com.nagare.balkrishna.omkar.programminginterviewprepguide.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.CustomClipboardManager;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.BuildConfig;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;

/**
 * Created by OMKARNAGARE on 6/4/2017.
 */

public class AboutMeDialog extends AlertDialog implements View.OnClickListener{

    private static final String TAG = "AboutMeDialog";

    public AboutMeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_me);

        TextView appVersion = (TextView) findViewById(R.id.app_version);
        appVersion.setText("Version "+ BuildConfig.VERSION_NAME);

        FloatingActionButton fabFacebook  = (FloatingActionButton) findViewById(R.id.fab_facebook);
        FloatingActionButton fabGoogle  = (FloatingActionButton) findViewById(R.id.fab_google);
        FloatingActionButton fabTwitter  = (FloatingActionButton) findViewById(R.id.fab_twitter);
        FloatingActionButton fabQuora  = (FloatingActionButton) findViewById(R.id.fab_quora);
        FloatingActionButton fabWhatsapp  = (FloatingActionButton) findViewById(R.id.fab_whatsapp);
        FloatingActionButton fabTumblr  = (FloatingActionButton) findViewById(R.id.fab_tumblr);
        FloatingActionButton fabLinkedin  = (FloatingActionButton) findViewById(R.id.fab_linkedin);

        fabFacebook.setOnClickListener(this);
        fabGoogle.setOnClickListener(this);
        fabTwitter.setOnClickListener(this);
        fabQuora.setOnClickListener(this);
        fabWhatsapp.setOnClickListener(this);
        fabTumblr.setOnClickListener(this);
        fabLinkedin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        CustomClipboardManager.copyToClipboard("https://play.google.com/store/apps/details?id="+ ProgrammingInterviewPrepGuideApp.getAppContext().getPackageName());
        switch (view.getId()){

            case R.id.fab_facebook:
                openBrowserWithURL("https://www.facebook.com/");
                break;
            case R.id.fab_google:
                openBrowserWithURL("https://plus.google.com/");
                break;
            case R.id.fab_twitter:
                openBrowserWithURL("https://twitter.com/Twitter");
                break;
            case R.id.fab_quora:
                openBrowserWithURL("https://www.quora.com/");
                break;
            case R.id.fab_whatsapp:
                startWhatsapp();
                break;
            case R.id.fab_tumblr:
                openBrowserWithURL("https://twitter.com/tumblr");
                break;
            case R.id.fab_linkedin:
                openBrowserWithURL("https://in.linkedin.com/");
                break;
            default:
                Log.d(TAG, "onClick: No such button");
                break;

        }
    }

    private void startWhatsapp() {

        Context context = ProgrammingInterviewPrepGuideApp.getAppContext();
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+ context.getPackageName());
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Whatsapp is not installed.", Toast.LENGTH_LONG).show();
        }

    }

    private void openBrowserWithURL(String url) {

        Context context = ProgrammingInterviewPrepGuideApp.getAppContext();

        Toast.makeText(context, "App URL is copied to the Clipboard.", Toast.LENGTH_LONG).show();
        Toast.makeText(context, "Please paste the copied URL to share.", Toast.LENGTH_LONG).show();

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);

    }

}
