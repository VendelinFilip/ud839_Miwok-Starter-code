package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);


        //create arrayList of words
        final ArrayList<Word> numbers = new ArrayList<Word>();
        //words.add("one");
        numbers.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        //words.add("two");
        numbers.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        //words.add("three");
        numbers.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        //words.add("four");
        numbers.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        //words.add("five");
        numbers.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        //words.add("six");
        numbers.add(new Word("six", "temmokkka", R.drawable.number_six, R.raw.number_six));
        //words.add("seven");
        numbers.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        //words.add("eight");
        numbers.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        //words.add("nine");
        numbers.add(new Word("nine", "wo´e", R.drawable.number_nine, R.raw.number_nine));
        //number.add("ten");
        numbers.add(new Word("ten", "na´aacha", R.drawable.number_ten, R.raw.number_ten));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, numbers, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_words.xmlayout file.
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the {@link Word} object at the given position the user clicked on
                Word words = numbers.get(position);

                //Release the media player if it currently exists because we are about to play a different sound file.
                releaseMediaPlayer();

                //Create and setup the {@link MediaPlayer} for the audio resource associated with the current word
                mediaPlayer = MediaPlayer.create(getApplicationContext(), words.getMiwokPronunciation());

                // Start the audio file
                mediaPlayer.start();

                //Setup a listener on the media player, so that we can stop and release the
                //media player once the sound has finished playing.
                mediaPlayer.setOnCompletionListener(mCompletionListener);

            }
        });


        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is stopped, release the media player resources because we won´t be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.
                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
}