// ======================================================================
// Author: Cody Thompson
// Date: 09-12-2024
// Desc: RGB color app
// ======================================================================

package com.ctom314.homework02_program1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.graphics.Color;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    // GUI variables
    View bg_j_main;

    TextView tv_j_redLabel;
    TextView tv_j_greenLabel;
    TextView tv_j_blueLabel;
    TextView tv_j_redValue;
    TextView tv_j_greenValue;
    TextView tv_j_blueValue;
    TextView tv_j_hexLabel;
    TextView tv_j_hexValue;

    SeekBar sb_j_redBar;
    SeekBar sb_j_greenBar;
    SeekBar sb_j_blueBar;

    Button btn_j_saveColor;

    ListView lv_j_savedColors;

    ArrayList<ColorInfo> colorList;
    
    ColorListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Setup GUI and Java connection
        bg_j_main = findViewById(R.id.bg_v_main);
        tv_j_redLabel = findViewById(R.id.tv_v_redLabel);
        tv_j_greenLabel = findViewById(R.id.tv_v_greenLabel);
        tv_j_blueLabel = findViewById(R.id.tv_v_blueLabel);
        tv_j_redValue = findViewById(R.id.tv_v_redValue);
        tv_j_greenValue = findViewById(R.id.tv_v_greenValue);
        tv_j_blueValue = findViewById(R.id.tv_v_blueValue);
        tv_j_hexLabel = findViewById(R.id.tv_v_hexLabel);
        tv_j_hexValue = findViewById(R.id.tv_v_hexValue);
        btn_j_saveColor = findViewById(R.id.btn_v_saveColor);
        lv_j_savedColors = findViewById(R.id.lv_v_savedColors);
        sb_j_redBar = findViewById(R.id.sb_v_redBar);
        sb_j_greenBar = findViewById(R.id.sb_v_greenBar);
        sb_j_blueBar = findViewById(R.id.sb_v_blueBar);

        // Setup seekbars
        sb_j_redBar.setProgress(255);
        sb_j_greenBar.setProgress(255);
        sb_j_blueBar.setProgress(255);

        saveColorButtonClickEvent();

        seekbarChangeEvents();

        savedColorsClickEvent();

        // Initialize the color list
        colorList = new ArrayList<ColorInfo>();
        
        fillListView();

        // Make sure the status bar is a static color (Slightly darker purple than header)
        getWindow().setStatusBarColor(Color.parseColor("#4900B0"));

    }

    // Save the color
    private void saveColorButtonClickEvent()
    {
        btn_j_saveColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Add the color to the list
                addColor(
                        getRGBValue(tv_j_redValue),
                        getRGBValue(tv_j_greenValue),
                        getRGBValue(tv_j_blueValue)
                );

                // Reset everything (minus the saved colors) to the default values
                resetValues();
            }
        });
    }

    private void resetValues()
    {
        // Reset Seekbars
        sb_j_redBar.setProgress(255);
        sb_j_greenBar.setProgress(255);
        sb_j_blueBar.setProgress(255);

        // Reset TextViews
        tv_j_redValue.setText("255");
        tv_j_greenValue.setText("255");
        tv_j_blueValue.setText("255");

        // Reset hex value
        tv_j_hexValue.setText("#FFFFFF");

        // Reset background color
        bg_j_main.setBackgroundColor(Color.rgb(255, 255, 255));
    }

    // Add the color to the list
    private void addColor(Integer red, Integer green, Integer blue)
    {
        // Create a new color object
        ColorInfo newColor = new ColorInfo(red, green, blue);

        // Add the color to the list
        colorList.add(newColor);

        // Log the color
        Log.d("Color", "New Color saved successfully. HEX: " + newColor.getHex());

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();

    }

    // Change the TextView colors when the background is too dark/light
    private void updateTextColor()
    {
        int red = getRGBValue(tv_j_redValue);
        int green = getRGBValue(tv_j_greenValue);
        int blue = getRGBValue(tv_j_blueValue);

        // If the RGB values are all below the threshold, set the text color to white
        int textColor;
        int threshold = 150;

        if (red < threshold && green < threshold && blue < threshold)
        {
            // BG is dark
            textColor = Color.WHITE;
        }
        else
        {
            // BG is light
            textColor = Color.BLACK;
        }

        // Set the text color of the TextViews
        tv_j_redLabel.setTextColor(textColor);
        tv_j_greenLabel.setTextColor(textColor);
        tv_j_blueLabel.setTextColor(textColor);
        tv_j_redValue.setTextColor(textColor);
        tv_j_greenValue.setTextColor(textColor);
        tv_j_blueValue.setTextColor(textColor);
        tv_j_hexLabel.setTextColor(textColor);
        tv_j_hexValue.setTextColor(textColor);
    }

    // Change the RGB values when the user moves the seekbars
    private void seekbarChangeEvents()
    {
        // ======================================================================
        //                              Red SeekBar
        // ======================================================================
        sb_j_redBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            // When the user moves the SeekBar, update the red value
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                // Update the red value
                tv_j_redValue.setText(String.valueOf(i));

                // Update the hex value
                tv_j_hexValue.setText(
                        // Format the hex value using the red, green, and blue values
                        String.format("#%02X%02X%02X",
                                i, getRGBValue(tv_j_greenValue),
                                getRGBValue(tv_j_blueValue)
                        )
                );

                // Set the background color of the main view
                bg_j_main.setBackgroundColor(
                        // Create a new color
                        Color.rgb(
                                getRGBValue(tv_j_redValue),
                                getRGBValue(tv_j_greenValue),
                                getRGBValue(tv_j_blueValue)
                        )
                );

                // Change the text color if the background is too dark/light
                updateTextColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        // ======================================================================
        //                              Green SeekBar
        // ======================================================================
        sb_j_greenBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            // When the user moves the SeekBar, update the green value
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                // Update the green value
                tv_j_greenValue.setText(String.valueOf(i));

                // Update the hex value
                tv_j_hexValue.setText(
                        // Format the hex value
                        String.format("#%02X%02X%02X",
                                getRGBValue(tv_j_redValue), i,
                                getRGBValue(tv_j_blueValue)
                        )
                );

                // Set the background color of the main view
                bg_j_main.setBackgroundColor(
                        // Create a new color
                        Color.rgb(
                                getRGBValue(tv_j_redValue),
                                getRGBValue(tv_j_greenValue),
                                getRGBValue(tv_j_blueValue)
                        )
                );

                // Change the text color if the background is too dark/light
                updateTextColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        // ======================================================================
        //                              Blue SeekBar
        // ======================================================================
        sb_j_blueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            // When the user moves the SeekBar, update the blue value
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                // Update the blue value
                tv_j_blueValue.setText(String.valueOf(i));

                // Update the hex value
                tv_j_hexValue.setText(
                        // Format the hex value
                        String.format("#%02X%02X%02X",
                                getRGBValue(tv_j_redValue),
                                getRGBValue(tv_j_greenValue), i
                        )
                );

                // Set the background color of the main view
                bg_j_main.setBackgroundColor(
                        // Create a new color
                        Color.rgb(
                                getRGBValue(tv_j_redValue),
                                getRGBValue(tv_j_greenValue),
                                getRGBValue(tv_j_blueValue)
                        )
                );

                // Change the text color if the background is too dark/light
                updateTextColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

    }

    // When the user clicks on a saved color, display the color
    private void savedColorsClickEvent()
    {
        lv_j_savedColors.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Get the color object at the specified index
                ColorInfo color = colorList.get(i);

                // Set the progress of the SeekBars to the color RGB values
                sb_j_redBar.setProgress(color.getRed());
                sb_j_greenBar.setProgress(color.getGreen());
                sb_j_blueBar.setProgress(color.getBlue());

                // Set the text of the RGB values to the color RGB values
                tv_j_redValue.setText(String.valueOf(color.getRed()));
                tv_j_greenValue.setText(String.valueOf(color.getGreen()));
                tv_j_blueValue.setText(String.valueOf(color.getBlue()));

                // Set the hex value to the color HEX value
                tv_j_hexValue.setText(color.getHex());

                // Set the background color of the main view to the color
                bg_j_main.setBackgroundColor(Color.rgb(
                        color.getRed(), color.getGreen(), color.getBlue()));

                // Change the text color if the background is too dark/light
                updateTextColor();

                // Log the loaded color
                Log.d("Color", "Loaded color: " + color.getHex());
            }
        });
    }

    // Get RGB values from the TextViews and storing them as ints
    private int getRGBValue(TextView tv)
    {
        return Integer.parseInt(tv.getText().toString());
    }

    private void fillListView()
    {
        // Create a new adapter
        adapter = new ColorListAdapter(this, colorList);

        // Set the adapter to the list view
        lv_j_savedColors.setAdapter(adapter);
    }
}
