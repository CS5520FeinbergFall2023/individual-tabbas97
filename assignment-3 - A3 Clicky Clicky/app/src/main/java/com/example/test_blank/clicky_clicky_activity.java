package com.example.test_blank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class clicky_clicky_activity extends AppCompatActivity {
    
    // Set up a map of buttons to their respective ids
    java.util.Map<String, String> buttonMap = new java.util.HashMap<String, String>() {{
        put("buttonA", "A");
        put("buttonB", "B");
        put("buttonC", "C");
        put("buttonD", "D");
        put("buttonE", "E");
        put("buttonF", "F");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        
//        // Set up a map of buttons to their respective ids
//        buttonMap.put("buttonA", "A");
//        buttonMap.put("buttonB", "B");
//        buttonMap.put("buttonC", "C");
//        buttonMap.put("buttonD", "D");
//        buttonMap.put("buttonE", "E");
//        buttonMap.put("buttonF", "F");
        
        // Set up the button listeners
        for (String buttonId : buttonMap.keySet()) {
            android.widget.Button button = findViewById(getResources().getIdentifier(buttonId, "id", getPackageName()));
            button.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    updateText(buttonId);
                }
            });
        }
        
    }
    
    protected void updateText(String buttonId) {
        
        // Get the button text
        String buttonText = "Pressed : " + buttonMap.get(buttonId);
        
        // Get the text view
        android.widget.TextView textView = findViewById(R.id.pressed_button_text_view);
        
        // Update the text view
        textView.setText(buttonText);
    }
    
}