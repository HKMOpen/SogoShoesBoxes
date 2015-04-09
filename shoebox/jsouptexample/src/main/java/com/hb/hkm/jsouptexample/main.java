package com.hb.hkm.jsouptexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hesk on 2/27/15.
 */
public class main extends ActionBarActivity {
    protected EditText respText;
    protected ParseURL process;

    class henc extends enhancedLife {

        @Override
        public void parserStart(ParseURL parse) {

        }

        @Override
        public void parserDone(ParseURL parse) {

        }
    }

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.editorchecker);
        final EditText edtUrl = (EditText) findViewById(R.id.edtURL);
        edtUrl.setText("http://store.hypebeast.com/");
        Button btnGo = (Button) findViewById(R.id.btnGo);
        respText = (EditText) findViewById(R.id.edtResp);
        process = new ParseURL().setTextField(respText);
        process.setLifeCycle(new henc());
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String siteUrl = edtUrl.getText().toString();
                process.execute(new String[]{siteUrl});
            }
        });
    }

}
