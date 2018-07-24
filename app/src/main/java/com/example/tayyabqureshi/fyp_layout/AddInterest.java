package com.example.tayyabqureshi.fyp_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.gujun.android.taggroup.TagGroup;

public class AddInterest extends AppCompatActivity {


    String[] arr={"football","Sports","Games","Lecture"};

    Button btn_interest;
    EditText edt_interest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interest);

        btn_interest=(Button) findViewById(R.id.btn_interest);
        edt_interest=(EditText)findViewById(R.id.edt_interest);


        TagGroup mTagGroup = (TagGroup)  findViewById(R.id.tag_group);
        mTagGroup.setTags(arr);



        btn_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
