package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    MediaPlayer mediaPlayer;

    /** Resource ID for the background color for this list of words */
    private int mColorResourceId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param words A List of Word objects to display in a list
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //convertView = recycleable views
        View listItemView = convertView;
        if(listItemView == null) {
            // if there are no recycable views it has to create a new one
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link AndroidWord} object located at this position in the list
        Word currentAndroidWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the default name from the current Word object and
        // set this text on the name TextView
        englishTextView.setText(currentAndroidWord.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the miwok name from the current Word object and
        // set this text on the name TextView
        miwokTextView.setText(currentAndroidWord.getMiwokTranslation());

        if (currentAndroidWord.hasImage()){
            // Find the ImageView in the list_item.xml layout with the ID example_image_view
            ImageView exampleImageView = (ImageView) listItemView.findViewById(R.id.example_image_view);
            // Get the image from the current Word object and
            // set it to the right image
            exampleImageView.setImageResource(currentAndroidWord.getImageResourceId());
            // Set the image to be visible if it wasn´t before
            exampleImageView.setVisibility(View.VISIBLE);
        }

        else{
            // Find the ImageView in the list_item.xml layout with the ID example_image_view
            ImageView exampleImageView = (ImageView) listItemView.findViewById(R.id.example_image_view);
            // Set the image to be gone
            exampleImageView.setVisibility(View.GONE);
        }

        // Find the LinearLayout which color you want to change.
        LinearLayout listLinearLayout = (LinearLayout)listItemView.findViewById(R.id.list_item);
        // Change its background
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        listLinearLayout.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}