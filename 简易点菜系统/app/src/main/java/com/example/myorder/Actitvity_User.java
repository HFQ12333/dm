package com.example.myorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actitvity_User extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    Intent intent;
    String user;
    TextView txv;
    ListView listView;
    String s = "";
    String st;
    private List<String> data = new ArrayList<>();
    List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list1 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list2 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list3 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list4 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list5 = new ArrayList<Map<String, Object>>();
    Map<String, Object> str;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_user);

        xianglai();
        laodifang();
        laoliujia();
        xiaoyangjia();
        xueyoujia();

        intent = getIntent();
        user = intent.getStringExtra("extra_user");
        txv = findViewById(R.id.user);
        txv.setText("欢迎用户："+user);

        Resources resources = getResources();
        String[] str = resources.getStringArray(R.array.cai);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        for (String a:str){
            data.add(a);
        }
        Spinner spinner = findViewById(R.id.spinner3);
        spinner.setAdapter(adapter);
        listView = findViewById(R.id.dianjia);
        spinner.setOnItemSelectedListener(this);
        listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.d("name",String.valueOf(position));
        str = list.get(position);
        st = (String) str.get("name");
        s +=st;
        AlertDialog.Builder builder = new AlertDialog.Builder(Actitvity_User.this);
        builder.setMessage("确定订购？");
        builder.setTitle("温馨提示:");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Actitvity_User.this,"你已订购："+st,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("评论", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.putExtra("data",st);
                intent.setClass(Actitvity_User.this,com.example.myorder.activity_pinglun.class);
                startActivity(intent);
            }
        });
        builder.show();
    }
    public void OnClick(View e){
        TextView textView = findViewById(R.id.textView11);
        textView.setText("您订购所有的菜品为："+s);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String num = data.get(position);
        switch (num){
            case "请选择":
                break;
            case "香莱菜馆":
                list = list1;
                break;
            case "老刘家串烧店":
                list = list3;
                break;
            case "老地方菜馆":
                list = list2;
                break;
            case "晓阳家火锅店":
                list = list4;
                break;
            case "学友海鲜店":
                list = list5;
                break;
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.list_item11,new String[]{"name","age","pic"},new int[]{R.id.t1,R.id.t2,R.id.t3});
        listView.setAdapter(simpleAdapter);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void xianglai(){
        Resources resources = getResources();
        String[] str = resources.getStringArray(R.array.Name香莱);
        String[] str1 = resources.getStringArray(R.array.Flavor香莱);
        String[] str2 = resources.getStringArray(R.array.Ingredients香莱);
        //遍历集合存到list里面
        for(int i = 0;i<str.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name",str[i]);
            map.put("age",str1[i]);
            map.put("pic",str2[i]);
            list1.add(map);
        }
    }
    public void laodifang(){
        Resources resources = getResources();
        String[] str = resources.getStringArray(R.array.Name老地方菜馆);
        String[] str1 = resources.getStringArray(R.array.Flavor老地方菜馆);
        String[] str2 = resources.getStringArray(R.array.Ingredients老地方菜馆);
        //遍历集合存到list里面
        for(int i = 0;i<str.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name",str[i]);
            map.put("age",str1[i]);
            map.put("pic",str2[i]);
            list2.add(map);
        }
    }
    public void laoliujia(){
        Resources resources = getResources();
        String[] str = resources.getStringArray(R.array.Name老刘家串烧店);
        String[] str1 = resources.getStringArray(R.array.Flavor老刘家串烧店);
        String[] str2 = resources.getStringArray(R.array.Ingredients老刘家串烧店);
        //遍历集合存到list里面
        for(int i = 0;i<str.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name",str[i]);
            map.put("age",str1[i]);
            map.put("pic",str2[i]);
            list3.add(map);
        }
    }
    public void xiaoyangjia(){
        Resources resources = getResources();
        String[] str = resources.getStringArray(R.array.Name晓阳家火锅店);
        String[] str1 = resources.getStringArray(R.array.Flavor晓阳家火锅店);
        String[] str2 = resources.getStringArray(R.array.Ingredients晓阳家火锅店);
        //遍历集合存到list里面
        for(int i = 0;i<str.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name",str[i]);
            map.put("age",str1[i]);
            map.put("pic",str2[i]);
            list4.add(map);
        }
    }
    public void xueyoujia(){
        Resources resources = getResources();
        String[] str = resources.getStringArray(R.array.Name学友海鲜店);
        String[] str1 = resources.getStringArray(R.array.Flavor学友海鲜店);
        String[] str2 = resources.getStringArray(R.array.Ingredients学友海鲜店);
        //遍历集合存到list里面
        for(int i = 0;i<str.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name",str[i]);
            map.put("age",str1[i]);
            map.put("pic",str2[i]);
            list5.add(map);
        }
    }
}
