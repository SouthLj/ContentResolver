package com.example.studyandroidchapter9_002;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ContentResolver contentResolver;
    Button insert = null;
    Button search = null;
   // Uri uri = Uri.parse("content://com.example.studyandroidchapter9_001/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取系统的ContentResolver对象
        contentResolver = getContentResolver();
        insert = (Button) findViewById(R.id.insert);
        search = (Button) findViewById(R.id.query);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入
                String word = ((EditText) findViewById(R.id.word))
                        .getText().toString();
                String detail = ((EditText) findViewById(R.id.detail))
                        .getText().toString();
                // 插入生词记录
                ContentValues values = new ContentValues();
                values.put(Words.Word.WORD, word);
                values.put(Words.Word.DETAIL, detail);
                contentResolver.insert(Words.Word.DICT_CONTENT_URI, values );
                // 显示提示信息
                Log.e("liujianDebug", "添加生词成功！");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入
                String key = ((EditText) findViewById(R.id.key))
                        .getText().toString();
                // 执行查询
                Cursor cursor = contentResolver.query(
                        Words.Word.DICT_CONTENT_URI, (null),
                        "word like ? or detail like ?", new String[]{
                                "%"+key+"%", "%"+key+"%"
                        }, null);
                // 创建一个Bundle对象
                Bundle data = new Bundle();
                data.putSerializable("data", converCursorToList(cursor));
                // 创建一个Intent
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Map<String, String>> converCursorToList(Cursor cursor){
        ArrayList<Map<String, String>> result =
                new ArrayList<Map<String, String>>();
        // 遍历Cursor结果集
        while (cursor.moveToNext()){
            // 将结果集中的数据存入ArrayList
            Map<String, String> map = new HashMap<String, String>();
            //取出查询记录中的第2列， 第3列
            map.put(Words.Word.WORD, cursor.getString(1));
            map.put(Words.Word.DETAIL, cursor.getString(2));
            result.add(map);
        }
        return result;
    }

   /* public void query(View view){
        Cursor c = contentResolver.query(uri, null, "query_where", null, null);
        Log.e("liujianDebug", "远程ContentProvider返回的Cursor为："+c);
    }
    public void insert(View view){
        ContentValues values = new ContentValues();
        values.put("name", "liujian");
        Uri newUri = contentResolver.insert(uri, values);
        Log.e("liujianDebug", "远程ContentProvider新插入的记录的Uri为：" + newUri);
    }
    public void update(View view){
        ContentValues values = new ContentValues();
        values.put("name", "liujianjian");
        int count = contentResolver.update(uri, values, "update_where", null);
        Log.e("liujianDebug", "远程ContentProvider更新记录数为：" +count);
    }
    public void delete(View view){
        int count = contentResolver.delete(uri, "delete_where", null);
        Log.e("liujianDebug", "远程ContentProvider删除记录数为："+count);
    }*/
}
