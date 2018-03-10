package aashna.com.aashna.aashna_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import aashna.com.aashna.R;

public class Forgot_password_Activity extends AppCompatActivity {

    Button  submit;
    TextView login_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        login_title = (TextView) findViewById(R.id.txt_retitle);

        login_title.setText("AASHNA");

        submit = (Button) findViewById(R.id.btn_reset);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}
