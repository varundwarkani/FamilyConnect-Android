package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class textviews extends AppCompatActivity {

    int size=5;
    String ab[] = new String[10];
    EditText[] tv = new EditText[size];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textviews);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layouttt);
        final EditText edit = (EditText) findViewById(R.id.child);


        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String ed;
                ed = edit.getText().toString();
                if (ed.equals(""))
                {
                    size = 1;
                    linearLayout.removeAllViews();
                    edit.setVisibility(View.VISIBLE);
                    linearLayout.addView(edit);
                }
                else {
                    Map<String, Integer> idsMap = new Map<String, Integer>() {
                        @Override
                        public int size() {
                            return 0;
                        }
                        @Override
                        public boolean isEmpty() {
                            return false;
                        }
                        @Override
                        public boolean containsKey(Object key) {
                            return false;
                        }
                        @Override
                        public boolean containsValue(Object value) {
                            return false;
                        }
                        @Override
                        public Integer get(Object key) {
                            return null;
                        }
                        @Override
                        public Integer put(String key, Integer value) {
                            return null;
                        }
                        @Override
                        public Integer remove(Object key) {
                            return null;
                        }
                        @Override
                        public void putAll(@NonNull Map<? extends String, ? extends Integer> m) {
                        }
                        @Override
                        public void clear() {
                        }
                        @NonNull
                        @Override
                        public Set<String> keySet() {
                            return null;
                        }
                        @NonNull
                        @Override
                        public Collection<Integer> values() {
                            return null;
                        }
                        @NonNull
                        @Override
                        public Set<Entry<String, Integer>> entrySet() {
                            return null;
                        }
                    };
                    size = Integer.parseInt(ed);
                    EditText temp;
                    for (int i = 0; i < size; i++) {
                        temp = new EditText(textviews.this);
                        temp.setText("Alarm: " + i);
                        String tname = "task" + Integer.toString(i);
                        ab[i] = temp.getText().toString();
                        linearLayout.addView(temp);
                        tv[i] = temp;
                        tv[i].setId(i);
                        idsMap.put(tname,i);
                    }
                }
            }
        });
    }

    public void buttt(View view) {
        for (int i = 0; i < size; i++) {
            String a;
            a = tv[i].getText().toString();
            Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
        }
    }
}
