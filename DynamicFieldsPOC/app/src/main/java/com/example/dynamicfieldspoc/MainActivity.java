package com.example.dynamicfieldspoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private TextView textView;
    private EditText enterName;
    private EditText date;
    private Button submit;
    private RadioGroup rg;
    private String regexFormat;
    private Spinner spinner;
    private String text, type = "";
    private Calendar myCalendar;
    private String dateFormat;
    private JSONObject obj;
    private JSONObject section1;
    private int data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        final int[] prevTextViewId = {0};
        myCalendar = Calendar.getInstance();

        RelativeLayout relativeLayout = findViewById(R.id.myLayout1);

        String mJSONURLString = "https://api.myjson.com/bins/xvdi3";

        for (int i = 0; i < 1; i++) {
            final ImageView textView = new ImageView(this);
            textView.setImageResource(R.drawable.avatar);

            int curTextViewId = prevTextViewId[0] + 1;
            textView.setId(curTextViewId);
            final RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(225,
                            225);

            params.addRule(RelativeLayout.BELOW, prevTextViewId[0]);
            params.topMargin = 35;
            params.leftMargin = 50;
            textView.setLayoutParams(params);

            prevTextViewId[0] = curTextViewId;
            relativeLayout.addView(textView, params);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                response -> {
                    JSONArray jsonObject = null;
                    try {
                        jsonObject = response.getJSONArray("data");
                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject object = jsonObject.getJSONObject(i);

                            type = object.optString("type");
                            text = object.optString("data");
                            Log.e("MainActivity", type + " " + text + " " + jsonObject.length());

                            switch (type) {
                                case "Textview": {
                                    textView = new TextView(this);
                                    textView.setText(text);
                                    textView.setTextColor(0xff00147e);

                                    int curTextViewId = prevTextViewId[0] + 1;
                                    textView.setTag("");
                                    textView.setId(curTextViewId);
                                    final RelativeLayout.LayoutParams params =
                                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

                                    params.addRule(RelativeLayout.BELOW, prevTextViewId[0]);
                                    params.topMargin = 35;
                                    params.leftMargin = 50;
                                    textView.setLayoutParams(params);

                                    prevTextViewId[0] = curTextViewId;
                                    relativeLayout.addView(textView, params);
                                    break;
                                }

                                case "Edittext": {
                                    enterName = new EditText(this);
                                    String placeHolder = object.getString("placeholderText");
                                    enterName.setHint(placeHolder);
                                    enterName.setPadding(20, 20, 20, 20);

                                    int curTextViewId = prevTextViewId[0] + 1;
                                    enterName.setId(curTextViewId);
                                    data = curTextViewId;
                                    enterName.setBackgroundResource(R.drawable.rounded_border_edittext);

                                    regexFormat = object.getString("validation");

                                    final RelativeLayout.LayoutParams params =
                                            new RelativeLayout.LayoutParams(800,
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

                                    params.addRule(RelativeLayout.BELOW, prevTextViewId[0]);
                                    params.topMargin = 50;
                                    params.leftMargin = 50;
                                    enterName.setLayoutParams(params);

                                    prevTextViewId[0] = curTextViewId;
                                    relativeLayout.addView(enterName, params);

                                    if (object.getBoolean("isCamera")) {
                                        final ImageView camera = new ImageView(this);
                                        camera.setImageResource(R.drawable.camera);

                                        curTextViewId = prevTextViewId[0] + 1;
                                        camera.setId(curTextViewId);
                                        final RelativeLayout.LayoutParams paramsCamera =
                                                new RelativeLayout.LayoutParams(75,
                                                        75);

                                        paramsCamera.addRule(RelativeLayout.ALIGN_RIGHT, prevTextViewId[0]);
                                        paramsCamera.addRule(RelativeLayout.ALIGN_TOP, prevTextViewId[0]);
                                        paramsCamera.addRule(RelativeLayout.ALIGN_BOTTOM, prevTextViewId[0]);
                                        paramsCamera.topMargin = 20;
                                        paramsCamera.rightMargin = 35;
                                        paramsCamera.bottomMargin = 20;
                                        camera.setLayoutParams(paramsCamera);

                                        relativeLayout.addView(camera, paramsCamera);
                                    }

                                    break;
                                }

                                case "Date": {
                                    for (int j=0; j<20; j++){
                                        date = new EditText(this);
                                        String placeHolder = object.getString("placeholderText");
                                        date.setHint(placeHolder);
                                        date.setPadding(20, 20, 20, 20);

                                        int curTextViewId = prevTextViewId[0] + 1;
                                        date.setId(curTextViewId);
                                        date.setBackgroundResource(R.drawable.rounded_border_edittext);

                                        dateFormat = object.getString("validation");

                                        DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                                  int dayOfMonth) {
                                                myCalendar.set(Calendar.YEAR, year);
                                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                                updateLabel();
                                            }

                                        };

                                        date.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                new DatePickerDialog(MainActivity.this, dateDialog, myCalendar
                                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                            }
                                        });

                                        final RelativeLayout.LayoutParams params =
                                                new RelativeLayout.LayoutParams(500,
                                                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                                        params.addRule(RelativeLayout.BELOW, prevTextViewId[0]);
                                        params.topMargin = 50;
                                        params.leftMargin = 50;
                                        date.setLayoutParams(params);

                                        prevTextViewId[0] = curTextViewId;
                                        relativeLayout.addView(date, params);

                                        if (object.getBoolean("isCamera")) {
                                            final ImageView camera = new ImageView(this);
                                            camera.setImageResource(R.drawable.camera);

                                            curTextViewId = prevTextViewId[0] + 1;
                                            camera.setId(curTextViewId);
                                            final RelativeLayout.LayoutParams paramsCamera =
                                                    new RelativeLayout.LayoutParams(75,
                                                            75);

                                            paramsCamera.addRule(RelativeLayout.ALIGN_RIGHT, prevTextViewId[0]);
                                            paramsCamera.addRule(RelativeLayout.ALIGN_TOP, prevTextViewId[0]);
                                            paramsCamera.addRule(RelativeLayout.ALIGN_BOTTOM, prevTextViewId[0]);
                                            paramsCamera.topMargin = 20;
                                            paramsCamera.rightMargin = 35;
                                            paramsCamera.bottomMargin = 20;
                                            camera.setLayoutParams(paramsCamera);

                                            relativeLayout.addView(camera, paramsCamera);
                                        }
                                    }
                                    break;
                                }

                                case "Checkbox": {
                                    JSONArray checkbox = object.getJSONArray("data");
                                    rg = new RadioGroup(this);
                                    rg.setOrientation(RadioGroup.HORIZONTAL);
                                    int curTextViewId = prevTextViewId[0] + 1;
                                    rg.setId(curTextViewId);

                                    for (int j = 0; j < checkbox.length(); j++) {
                                        JSONObject radioJson = checkbox.getJSONObject(j);

                                        RadioButton[] rb = new RadioButton[checkbox.length()];
                                        rb[j] = new RadioButton(this);
                                        rb[j].setText(radioJson.getString("text"));
                                        rg.addView(rb[j]);
                                    }
                                    final RelativeLayout.LayoutParams params =
                                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

                                    params.addRule(RelativeLayout.BELOW, prevTextViewId[0]);
                                    params.topMargin = 50;
                                    params.leftMargin = 100;
                                    rg.setLayoutParams(params);
                                    prevTextViewId[0] = curTextViewId;
                                    relativeLayout.addView(rg, params);
                                    break;
                                }

                                case ("Dropdown"): {
                                    ArrayList<String> spinnerArray = new ArrayList<String>();
                                    JSONArray data = object.getJSONArray("data");
                                    for (int j=0; j<data.length(); j++){
                                        spinnerArray.add(data.getString(j));
                                    }

                                    spinner = new Spinner(this);

                                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            this, R.layout.spinner_item, spinnerArray) {
                                        @Override
                                        public boolean isEnabled(int position) {
                                            if (position == 0) {
                                                // Disable the first item from Spinner
                                                // First item will be use for hint
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        }

                                        @Override
                                        public View getDropDownView(int position, View convertView,
                                                                    ViewGroup parent) {
                                            View view = super.getDropDownView(position, convertView, parent);
                                            TextView tv = (TextView) view;
                                            if (position == 0) {
                                                // Set the hint text color gray
                                                tv.setTextColor(Color.GRAY);
                                            } else {
                                                tv.setTextColor(Color.BLACK);
                                            }
                                            return view;
                                        }
                                    };
                                    spinner.setAdapter(spinnerArrayAdapter);

                                    int curTextViewId = prevTextViewId[0] + 1;
                                    spinner.setId(curTextViewId);
                                    final RelativeLayout.LayoutParams params =
                                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

                                    params.addRule(RelativeLayout.BELOW, prevTextViewId[0]);
                                    params.topMargin = 50;
                                    params.leftMargin = 50;
                                    spinner.setLayoutParams(params);

                                    prevTextViewId[0] = curTextViewId;
                                    relativeLayout.addView(spinner, params);
                                    break;

                                }

                                case ("Submit"): {
                                    submit = new Button(this);
                                    submit.setText(text);
                                    submit.setBackgroundResource(R.drawable.rounded_border_button);

                                    int curTextViewId = prevTextViewId[0] + 1;
                                    submit.setId(curTextViewId);
                                    final RelativeLayout.LayoutParams params =
                                            new RelativeLayout.LayoutParams(500,
                                                    125);

                                    params.addRule(RelativeLayout.BELOW, prevTextViewId[0]);
                                    params.topMargin = 35;
                                    params.leftMargin = 100;
                                    submit.setLayoutParams(params);

                                    prevTextViewId[0] = curTextViewId;
                                    relativeLayout.addView(submit, params);

                                    submit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            final String name = enterName.getText().toString();
                                            Log.e("MainActivity", name);
                                            if (name.length() == 0) {
                                                enterName.requestFocus();
                                                enterName.setError("FIELD CANNOT BE EMPTY");
                                            } else {

                                                if (!name.matches(regexFormat)) {
                                                    enterName.requestFocus();
                                                    enterName.setError("ENTER ONLY ALPHANUMERIC CHARACTER");
                                                } else {
                                                    EditText dob = findViewById(data);
                                                    String string = dob.getText().toString();

                                                    Log.e("Main", string);
                                                    Toast.makeText(MainActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    });
                                    break;
                                }

                                default: {
                                    break;
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    if (error instanceof NetworkError) {

                    }
                }
        );
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);

    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
        date.setSelection(date.getText().length());
    }
}
