package aashna.com.aashna.S_ReportIssue;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import aashna.com.aashna.R;

import java.io.FileNotFoundException;

/**
 * Created by Dell on 17-Feb-18.
 */

public class ReportIssueActivity extends AppCompatActivity {

    EditText topic, desc;
    Button share;
    ImageView imageView;
    LinearLayout linearLayout;

    String tpc,des;
    String realPath;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "ReportIssueActivity";
    Uri selectedImageUri;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_issue_activity);

        topic = (EditText) findViewById(R.id.topic);
        desc = (EditText) findViewById(R.id.desc);
        imageView = (ImageView) findViewById(R.id.imageView);
        linearLayout = (LinearLayout) findViewById(R.id.parent);
        tv = (TextView) findViewById(R.id.myImageViewText);

        share = (Button) findViewById(R.id.share);


       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openFolder();
           }
       });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpc = topic.getText().toString();
                des = desc.getText().toString();
                if (tpc.isEmpty()) {
                    topic.setError("Please enter title");
                } else if (des.isEmpty()) {
                    desc.setError("Please enter description");
                } else if(selectedImageUri == null){
                    Toast.makeText(ReportIssueActivity.this, "Please Attach a picture!", Toast.LENGTH_LONG).show();
                }

                if(!tpc.isEmpty() && !des.isEmpty() && selectedImageUri !=null) {
                    try {
                        sendMessage(v);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Log.d("App", "other");

                }else {
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {


                // SDK < API11
                if (Build.VERSION.SDK_INT < 11)
                    realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                    // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19)
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                    // SDK > 19 (Android 4.4)
                else
                    realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

                // Get the url from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    Log.i(TAG, "Image Path : " + realPath);
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);
                    tv.setVisibility(View.GONE);
                }
            }
        }
    }

    public void sendEmail()
    {
        try
        {
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/image");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "" });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,tpc);
            Uri uri = Uri.parse("file://" + realPath);
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, des);
            emailIntent.setPackage("com.google.android.gm");
            this.startActivity(emailIntent);
        }
        catch (Throwable t)
        {
            Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void sendMessage(View v) throws FileNotFoundException {

        /*String whatsAppMessage = desc.getText().toString();

        //You can read the image from external drove too
        Uri uri = Uri.parse("file://" + realPath);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.setType("image/jpeg");
        //intent.setPackage("com.whatsapp");
        this.startActivity(Intent.createChooser(intent,"Choose a Messenger App "));
        startActivity(intent);*/
        try
        {
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/image");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "" });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,tpc);
            Uri uri = Uri.parse("file://" + realPath);
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, des);
            this.startActivity(Intent.createChooser(emailIntent," Select an App to share :"));
        }
        catch (Throwable t)
        {
            Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void openFolder()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


}