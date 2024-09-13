package com.ctom314.homework02_program1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.Color;

import java.util.ArrayList;

public class ColorListAdapter extends BaseAdapter
{
    Context context;
    ArrayList<ColorInfo> colors;

    // Constructor
    public ColorListAdapter(Context c, ArrayList<ColorInfo> ls)
    {
        context = c;
        colors = ls;
    }

    // Get the number of cells to create
    @Override
    public int getCount()
    {
        return colors.size();
    }

    // Get colors object at the specified index
    @Override
    public Object getItem(int i)
    {
        return colors.get(i);
    }

    // Get the specified colors object ID
    @Override
    public long getItemId(int i)
    {
        return i;
    }

    // Create the view for the cell
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = mInflater.inflate(R.layout.colorlistview_cell, null);
        }

        // Find the GUI elements in the listview XML file
        TextView redLabel = view.findViewById(R.id.tv_v_cc_redLabel);
        TextView greenLabel = view.findViewById(R.id.tv_v_cc_greenLabel);
        TextView blueLabel = view.findViewById(R.id.tv_v_cc_blueLabel);
        TextView hexLabel = view.findViewById(R.id.tv_v_cc_hexLabel);
        TextView redValue = view.findViewById(R.id.tv_v_cc_redValue);
        TextView greenValue = view.findViewById(R.id.tv_v_cc_greenValue);
        TextView blueValue = view.findViewById(R.id.tv_v_cc_blueValue);
        TextView hexValue = view.findViewById(R.id.tv_v_cc_hexValue);

        // Get the color object at the specified index
        ColorInfo color = colors.get(i);

        // Get the color values and convert them to strings
        String red = Integer.toString(color.getRed());
        String green = Integer.toString(color.getGreen());
        String blue = Integer.toString(color.getBlue());
        String hex = color.getHex();

        // Set the text of the GUI elements
        redValue.setText(red);
        greenValue.setText(green);
        blueValue.setText(blue);
        hexValue.setText(hex);

        // If the RGB values are less than the threshold, set the text color to white
        int textColor;
        int threshold = 158;

        if (color.getRed() < threshold && color.getGreen() < threshold
                && color.getBlue() < threshold)
        {
            textColor = Color.WHITE;
        }
        else
        {
            textColor = Color.BLACK;
        }

        // Set the text color of the TextViews
        redLabel.setTextColor(textColor);
        greenLabel.setTextColor(textColor);
        blueLabel.setTextColor(textColor);
        hexLabel.setTextColor(textColor);
        redValue.setTextColor(textColor);
        greenValue.setTextColor(textColor);
        blueValue.setTextColor(textColor);
        hexValue.setTextColor(textColor);

        // Set the background color of the view
        view.setBackgroundColor(Color.rgb(
                color.getRed(), color.getGreen(), color.getBlue()));

        return view;
    }
}
