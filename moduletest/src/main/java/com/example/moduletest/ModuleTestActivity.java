package com.example.moduletest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.annotation.Route;
import com.example.route.Router;



@Route(value = "/main/food")
public class ModuleTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_test);

        TextView textView=this.findViewById(R.id.test2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().startActivity(ModuleTestActivity.this,"/second/main");

            }
        });
    }
}