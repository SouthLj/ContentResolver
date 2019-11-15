package com.example.studyandroidchapter9_002;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ListView listView = (ListView) findViewById(R.id.show);
        Intent intent = getIntent();
        // 获取该Intent所携带的数据
        Bundle data = intent.getExtras();
        // 从Bundle包中获取数据
        @SuppressWarnings("unchecked")
        List<Map<String, String>> list = (List<Map<String, String>>) data.getSerializable("data");
        // 将List封装成SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.line, new String[]{"word", "detail"}
        , new int[] {R.id.word_1, R.id.detail_1});
        listView.setAdapter(adapter);
    }
}
