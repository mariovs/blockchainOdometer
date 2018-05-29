package com.mario22gmail.vasile.odometerblockchain;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

public class CustomQRDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Button cancelButton;
    public ImageView qRCodeImageView;
    private String valueToDisplayInQr;

    public CustomQRDialog(Activity a, String valueToDisplay)
    {
        super(a);
        c = a;
        valueToDisplayInQr = valueToDisplay;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_qr_dialog);
        cancelButton = (Button) findViewById(R.id.cancelQrDialogButton);
        qRCodeImageView = (ImageView) findViewById(R.id.QrDialogImageView);

        Bitmap qrCodeGenerated = QRCode.from(valueToDisplayInQr).bitmap();
        qRCodeImageView.setImageBitmap(qrCodeGenerated);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }
}
