package com.lsf.cal.calc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button caculateButton=(Button)findViewById(R.id.button);
        caculateButton.setOnClickListener(this);
        Button clearButton=(Button)findViewById(R.id.clear);
        clearButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.text);
        if(v.getId()==R.id.button) {
            CaculateEx caculateEx = new CaculateEx();
            String expreString = editText.getText().toString();
            expreString = caculateEx.suffix_expression(expreString);
            editText.setText(expreString);
        }else{
            editText.setText("");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
