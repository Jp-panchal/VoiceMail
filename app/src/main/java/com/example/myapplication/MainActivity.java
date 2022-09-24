package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
//import android.opengl.EGLExt;
import android.support.annotation.NonNull;
import android.os.Build;
//import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE =1 ;

    private TelephonyManager myTele;
    private MyPhoneCallListener mListener;

    EditText editTextAlex;
    TextView copyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        copyButton=findViewById(R.id.tttt);
        editTextAlex=findViewById(R.id.editText_main);
        //ImageButton button=findViewById(R.id.back);

        //create a telephone manager
        myTele=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);

        //check for telephone is enable
        if(isTelephonyEnabled()){
            checkForPhonePermissions();

            mListener=new MyPhoneCallListener();
            //myTele.listen(mListener,PhoneStateListener.LISTEN_CALL_STATE);
        }
        else{
            Toast.makeText(this, R.string.telephony_enabled, Toast.LENGTH_SHORT).show();
            disableCallButton();
        }
    }
    public void bb(View v){
        android.content.ClipboardManager clipboard=(android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip=android.content.ClipData.newPlainText("Text_Label",editTextAlex.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(),"Copied_That_Number", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v){
        String edit;
        if(editTextAlex.length()==0){
            ImageButton button=findViewById(R.id.back);
            button.setVisibility(View.VISIBLE);
        }
            if(v.getId()==R.id.num0) {
                edit=(editTextAlex.getText() + "0");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num1) {
                edit=(editTextAlex.getText() + "1");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num2) {
                edit=(editTextAlex.getText() + "2");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num3) {
                edit=(editTextAlex.getText() + "3");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num4) {
                edit=(editTextAlex.getText() + "4");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num5) {
                edit=(editTextAlex.getText() + "5");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num6) {
                edit=(editTextAlex.getText() + "6");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num77) {
                edit=(editTextAlex.getText() + "7");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num88) {
                edit=(editTextAlex.getText() + "8");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.num99) {
                edit=(editTextAlex.getText() + "9");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.numAA) {
                edit=(editTextAlex.getText() + "*");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.numBB) {
                edit=(editTextAlex.getText() + "#");
                editTextAlex.setText(edit);
            }
            if(v.getId()==R.id.back) {
                String str = editTextAlex.getText().toString();
                str = str.substring(0, str.length() - 1);
                editTextAlex.setText(str);
                if (str.length() == 0) {
                    ImageButton button = findViewById(R.id.back);
                    button.setVisibility(View.INVISIBLE);
                }
            }


        findViewById(R.id.back).setOnLongClickListener(v1 -> {
            editTextAlex.setText("");
            ImageButton button=findViewById(R.id.back);
            button.setVisibility(View.INVISIBLE);
            return true;
        });
        findViewById(R.id.back).setOnClickListener(v1 -> {
            editTextAlex.setText("");
            ImageButton button=findViewById(R.id.back);
            button.setVisibility(View.INVISIBLE);
        });
    }

    private boolean isTelephonyEnabled(){
        if(myTele != null){
            if(myTele.getSimState() == TelephonyManager.SIM_STATE_READY){
                return true;
            }
            else {
                Toast.makeText(this,"...", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void checkForPhonePermissions(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
        else{
            enableCallButton();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestcode,@NonNull String[] permission,@NonNull int[] grantResults){
            if(requestcode==MY_PERMISSIONS_REQUEST_CALL_PHONE){
                if(permission[0].equalsIgnoreCase(Manifest.permission.CALL_PHONE) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    enableCallButton();
                }
                else{
                    Toast.makeText(this, getString(R.string.failure_permission), Toast.LENGTH_LONG).show();
                    disableCallButton();
                }
            }
    }

    public void callNumber(View view){
        editTextAlex = findViewById(R.id.editText_main);
        String phoneNumber = String.format("tel: %s", editTextAlex.getText().toString());
        Toast.makeText(this, getString(R.string.dial_number) + phoneNumber, Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));
        if(callIntent.resolveActivity(getPackageManager()) != null){
            checkForPhonePermissions();
            startActivity(callIntent);
        }
        else{
            Log.e(TAG, "Cant Resolve app for Action call Intent.");
        }
    }

    private class MyPhoneCallListener extends PhoneStateListener {
        private boolean returningFromOfHook = false;

        private MyPhoneCallListener(){super();}

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            String massage = getString(R.string.phone_status);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    massage = massage + getString(R.string.ringing) + incomingNumber;
                    Toast.makeText(MainActivity.this, massage, Toast.LENGTH_SHORT).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    massage = massage + getString(R.string.offhook);
                    Toast.makeText(MainActivity.this, massage, Toast.LENGTH_SHORT).show();
                    returningFromOfHook = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    massage = massage + getString(R.string.idle);
                    Toast.makeText(MainActivity.this, massage, Toast.LENGTH_SHORT).show();
                    if (returningFromOfHook) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                    break;
                default:
                    massage = "Phone off";
                    Toast.makeText(MainActivity.this, massage, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
        private void disableCallButton() {
            Toast.makeText(this, R.string.phone_disabled, Toast.LENGTH_LONG).show();
            ImageButton callButton = findViewById(R.id.phone_icon);
            callButton.setVisibility(View.INVISIBLE);
        }

        private void enableCallButton() {
            ImageButton callButton = findViewById(R.id.phone_icon);
            callButton.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (isTelephonyEnabled()) {
                myTele.listen(mListener, PhoneStateListener.LISTEN_NONE);
            }
        }
}