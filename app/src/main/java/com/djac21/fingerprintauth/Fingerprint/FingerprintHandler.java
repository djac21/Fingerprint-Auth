package com.djac21.fingerprintauth.Fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.djac21.fingerprintauth.MainActivity;
import com.djac21.fingerprintauth.R;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private TextView textView;

    FingerprintHandler(Context mContext) {
        context = mContext;
    }

    void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Error:\n" + errString);
        textView.setTextColor(Color.RED);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Tip:\n" + helpString);
        textView.setTextColor(Color.BLUE);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed!");
        textView.setTextColor(Color.RED);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        ((Activity) context).finish();
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private void update(String error) {
        textView = ((Activity) context).findViewById(R.id.errorText);
        textView.setText(error);
    }
}