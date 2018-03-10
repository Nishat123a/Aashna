package aashna.com.aashna.H_DoctorAppointment;

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

import org.w3c.dom.Text;

import java.util.List;

import aashna.com.aashna.R;
import aashna.com.aashna.S_ReportIssue.RealPathUtil;

/**
 * Created by Dell on 27-Feb-18.
 */

public class Doctor_detail_activity extends AppCompatActivity {

    TextView doc_name, doc_address, doc_email, doc_contact,doc_timing;
    String n1, ad1,e1,c1,t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_detail_activity);

        Bundle bundle = getIntent().getExtras();

        n1 = bundle.getString("name");
        ad1 = bundle.getString("address");
        e1 = bundle.getString("email");
        c1 = bundle.getString("contact");
        t1 = bundle.getString("timing");

        doc_name = (TextView) findViewById(R.id.name);
        doc_address = (TextView) findViewById(R.id.address);
        doc_email = (TextView) findViewById(R.id.email);
        doc_contact = (TextView) findViewById(R.id.contact);
        doc_timing = (TextView) findViewById(R.id.timings);


        doc_name.setText(n1);
        doc_address.setText(ad1);
        doc_email.setText(e1);
        doc_contact.setText(c1);
        doc_timing.setText(t1);

        doc_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Send Mail to " + e1, Toast.LENGTH_SHORT).show();
                shareToGMail();
            }
        });

        doc_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Calling " + n1, Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void shareToGMail() {
        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/image");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[] {c1});

        this.startActivity(Intent.createChooser(emailIntent,"Sending email..."));

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


    }
