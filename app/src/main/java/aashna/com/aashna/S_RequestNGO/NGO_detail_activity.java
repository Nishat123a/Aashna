package aashna.com.aashna.S_RequestNGO;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import aashna.com.aashna.S_ReportIssue.RealPathUtil;

import java.util.List;

/**
 * Created by Dell on 27-Feb-18.
 */

public class NGO_detail_activity extends AppCompatActivity {

    TextView ngo_name, ngo_address, ngo_email, ngo_contact;
    String n1, ad1,e1,c1;
    EditText topic, desc;
    ImageView imageView;
    LinearLayout linearLayout;
    Button attch,mail,requst_ngo;
    String tpc,des;
    String realPath;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "ReportIssueActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_detail_activity);

        Bundle bundle = getIntent().getExtras();

        n1 = bundle.getString("name");
        ad1 = bundle.getString("address");
        e1 = bundle.getString("email");
        c1 = bundle.getString("contact");

        ngo_name = (TextView) findViewById(R.id.name);
        ngo_address = (TextView) findViewById(R.id.address);
        ngo_email = (TextView) findViewById(R.id.email);
        ngo_contact = (TextView) findViewById(R.id.contact);
        topic = (EditText) findViewById(R.id.topic);
        desc = (EditText) findViewById(R.id.desc);
        attch = (Button) findViewById(R.id.attachment);
        requst_ngo = (Button) findViewById(R.id.req);
        imageView = (ImageView) findViewById(R.id.imageView);
        linearLayout = (LinearLayout) findViewById(R.id.reqlayout);

        linearLayout.setVisibility(View.GONE);

        ngo_name.setText(n1);
        ngo_address.setText(ad1);
        ngo_email.setText(e1);
        ngo_contact.setText(c1);

        ngo_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Calling " + e1, Toast.LENGTH_SHORT).show();
            }
        });

        requst_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                requst_ngo.setVisibility(View.GONE);
            }
        });

        mail = (Button) findViewById(R.id.mail);

        attch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // sendEmail();
                shareToGMail();
                Log.d("App", "mail");
            }
        });
    }
    public void shareToGMail() {
        tpc = topic.getText().toString();
        des = desc.getText().toString();
        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/image");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {c1});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,tpc);
        Uri uri = Uri.parse("file://" + realPath);
        emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, des);

        this.startActivity(android.content.Intent.createChooser(emailIntent,"Sending email..."));

        final PackageManager pm = this.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        this.startActivity(emailIntent);
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
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    Log.i(TAG, "Image Path : " + realPath);
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }


    public void sendEmail()
    {
        try
        {
            Intent emailIntent=new Intent(Intent.ACTION_SEND);
           // final android.content.Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/image");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {c1});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,tpc);
            Uri uri = Uri.parse("file://" + realPath);
            emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, des);

            this.startActivity(android.content.Intent.createChooser(emailIntent,"Sending email..."));
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
        intent.setAction(android.content.Intent.ACTION_GET_CONTENT);
        startActivityForResult(android.content.Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    }
