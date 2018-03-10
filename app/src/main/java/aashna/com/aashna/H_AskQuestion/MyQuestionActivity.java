package aashna.com.aashna.H_AskQuestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import aashna.com.aashna.R;

/**
 * Created by Dell on 21-Feb-18.
 */

public class MyQuestionActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);

        btn = (Button) findViewById(R.id.btn_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQuestionActivity.this,LatestQuestionActivity.class);
                startActivity(intent);
            }
        });
    }
}
