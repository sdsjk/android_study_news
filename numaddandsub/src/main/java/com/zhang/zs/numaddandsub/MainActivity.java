package com.zhang.zs.numaddandsub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private AddAndSubView  add_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_sub = (AddAndSubView)findViewById(R.id.add_sub);
        add_sub.setOnButtonClickListener(new AddAndSubView.OnButtonClickListener() {
            @Override
            public void onSubNumberClick(View view, int value) {
                Toast.makeText(MainActivity.this, "减少"+value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddNumberClick(View view, int value) {
                Toast.makeText(MainActivity.this, "添加"+value, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
