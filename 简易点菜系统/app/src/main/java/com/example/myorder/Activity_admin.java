package com.example.myorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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


public class Activity_admin extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener {
    Intent intent;
    String user;
    TextView txv;
    Spinner spinner;
    ListView listView;
    List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list1 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list2 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list3 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list4 = new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> list5 = new ArrayList<Map<String, Object>>();
    private List<String> data = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        intent = getIntent();
        user = intent.getStringExtra("extra_admin");
        txv = findViewById(R.id.textView2);
        txv.setText("欢迎管理员："+user);

        Resources resources = getResources();
        listView = findViewById(R.id.List_View);
        String[] str = resources.getStringArray(R.array.cai);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        for (String a:str){
            data.add(a);
        }
        xianglai();
        laoliujia();
        laodifang();
        xiaoyangjia();
        xueyoujia();

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String num = data.get(position);
        Toast.makeText(this,num,Toast.LENGTH_SHORT).show();
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
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.list_item,new String[]{"name","age","pic"},new int[]{R.id.t1,R.id.t2,R.id.tv3});
        listView.setAdapter(simpleAdapter);
/*        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,da);
        adapter.notifyDataSetChanged();*/
        ListView listView = findViewById(R.id.List_View);
        listView.setAdapter(simpleAdapter);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.list_item,new String[]{"name","age","pic"},new int[]{R.id.t1,R.id.t2,R.id.tv3});
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_admin.this);
        builder.setMessage("确定删除？");
        builder.setTitle("温馨提示:");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                simpleAdapter.notifyDataSetChanged();
                listView.setAdapter(simpleAdapter);
                Toast.makeText(getBaseContext(),"删除列表项",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
        return false;
    }
    public void onClikAddOder(View e){
        intent = new Intent();
        intent.setClass(this,com.example.myorder.Admin_OrderAdd.class);
        startActivityForResult(intent,1);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnName = data.getStringExtra("data_returnName");
                    String returnFlavor = data.getStringExtra("data_returnFlavor");
                    String returnIngredients = data.getStringExtra("data_returnIngredients");
                    String returnData = data.getStringExtra("data_returnData");
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("name",returnName);
                    map.put("age",returnFlavor);
                    map.put("pic",returnIngredients);
                    switch (returnData){
                        case "香莱菜馆":
                            list1.add(map);
                             break;
                        case "老刘家串烧店":
                            list2.add(map);
                            break;
                        case "老地方菜馆":
                            list3.add(map);
                            break;
                        case "晓阳家火锅店":
                            list4.add(map);
                            break;
                        case "学友海鲜店":
                            list5.add(map);
                            break;
                    }
                }
        }
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
