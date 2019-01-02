package tttwdebwasmabtytt.projectk;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tttwdebwasmabtytt.projectk.utilities.Parser;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private Button button;
    private TextView text;

    // Other private variables
    Context context;
    AudioManager audioManager;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up variables
        context = getApplicationContext();
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE); // This lets us control volume
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE); // Need this to request permission to unmute device


        // Get ui elements
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);

    }

    @Override
    protected void onResume() {

        // Check if we have the permission we need(and if we need it anyway)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()){ // if we don't have permission
            // Set up the activity to request the permission
            requestPermission();
        } else { // If we do have the permission(or don't need it)
            // Set up the activity to unmute the device
            initialize();
        }

        super.onResume();

    }

    // Other methods

    /**
     * Initializes the page to function normally
     */
    private void initialize(){

        // Set up the text
        text.setText(R.string.text_have_permission);

        // Set button text
        button.setText(R.string.button_have_permission);

        // Make the button unmute the device
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unmuteDevice();
            }
        });

    }

    /**
     * Unmutes the device and sets the volume of the ringtone to maximum
     */
    private void unmuteDevice(){

        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL); // Removes silent

        while(audioManager.getStreamVolume(AudioManager.STREAM_RING) < audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)){ // While volume isnt max
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING, // Increase volume
                    audioManager.ADJUST_RAISE,
                    AudioManager.FLAG_SHOW_UI);
        }

    }

    /**
     * Requests for the user to give us permission to change Do Not Disturb settings
     * Adds additional text to the activity to request permission from the user.
     * Will make the button open the permissions page for Do Not Disturb.
     */
    private void requestPermission(){

        // Set the text to request permission
        text.setText(R.string.text_need_permission);

        // Change button text
        button.setText(R.string.button_need_permission);

        // Make the button open the permissions page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        android.provider.Settings
                                .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        });

    }

}
