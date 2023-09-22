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
        
        updateText("Pressed: -");
        
        // Set up the button listeners
        for (String buttonId : buttonMap.keySet()) {
            android.widget.Button button = findViewById(getResources().getIdentifier(buttonId, "id", getPackageName()));
            button.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    updateButtonName(buttonId);
                }
            });
        }
        
    }
    
    protected void updateButtonName(String buttonId) {
        
        // Get the button text
        String buttonText = "Pressed: " + buttonMap.get(buttonId);
        
        updateText(buttonText);
    }
    
    protected void updateText(String text) {
        
        // Get the text view
        android.widget.TextView textView = findViewById(R.id.pressed_button_text_view);
        
        // Update the text view
        textView.setText(text);
    }
    
}