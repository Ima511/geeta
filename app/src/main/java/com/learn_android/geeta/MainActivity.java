package com.learn_android.geeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView hindiText = (TextView) findViewById(R.id.hidiText);
        textView.setText((CharSequence) slokasArray().get(0).toString().trim());
        String  input = "";
        int numIterations = 701;
        for (int i = 0; i < numIterations; i++) {
            input = slokasArray().get(i).toString().trim();
            // do something with the input string
        }
        // Get the index of the first numerical value
        int index = input.indexOf("।।");

// Extract the Sanskrit text
        String sanskritText = input.substring(0, index + 2);

// Extract the Hindi translation
        String hindiTranslation = input.substring(index + 4);
          textView.setText(sanskritText.substring(0,sanskritText.length()-2));
          String hindi =   hindiTranslation.replaceAll("[a-zA-Z]+", "").replaceAll("\\d+", "").replace("©" , "").replace(",", "");
          hindiText.setText(hindi.trim().substring(8));
System.out.println("Sanskrit text: " + sanskritText);
        System.out.println("Hindi translation: " + hindiTranslation);
    }

    private  ArrayList slokasArray(){
        ArrayList<String> sb = new ArrayList<>();
        try {

            for (int chapter = 1; chapter <= 18; chapter++) {
                String fileName = "chapter_" + chapter + ".json";

                // Load JSON data from assets folder
                InputStream is = getAssets().open(fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, StandardCharsets.UTF_8);

                // Parse JSON data
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");

                // Extract contentSanskrit attribute from each object in the data array
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    JSONObject attributes = obj.getJSONObject("attributes");
                    String contentSanskrit = attributes.getString("contentSanskrit");
                    contentSanskrit = contentSanskrit.replaceFirst("[^\\p{Alnum}]+", "");
                    sb.add(contentSanskrit);
                }
            }


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        Log.d("Sloaks_Array",sb.size() + " ");
        return sb;
    }


}