package com.paxees.statussaver.forwaapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

    public static final String INTENT_KEY_USER = "USER";
    public static final String INTENT_KEY_TYPE = "TYPE";

    public static void makeCall(String to,Context context){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + to));
        context.startActivity(intent);
    }

   public static void openMapWith(String query, String latitude, String longitude, Context context){
        Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"?z=10&q="+query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    public static void sendEmail(String to, Context context){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",to, null));

        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static void sendSMS(String to, Context context){
        Uri uri = Uri.parse("smsto:"+to);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        context.startActivity(intent);
    }

}
