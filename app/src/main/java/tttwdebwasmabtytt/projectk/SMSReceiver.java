package tttwdebwasmabtytt.projectk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import tttwdebwasmabtytt.projectk.utilities.Parser;

public class SMSReceiver extends BroadcastReceiver {

    AudioManager audioManager;
    @Override
    public void onReceive(Context context, Intent intent) {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE); // This lets us control volume.
        String action = intent.getAction(); //The intent action name.

        if(action == null || action.equals(""))
        {
            //The action can't be empty or null.
            return;
        }

        Bundle bundle = intent.getExtras();     //This contains the sms content.
        SmsMessage[] messages;

        if(bundle != null)
        {
            try{
                Object[] pdus = (Object[]) bundle.get("pdus");           //PDU: Protocol Data Unit, holds the SMS.
                String format = bundle.getString("format");         //The format of the SMS sent.
                if(pdus !=null) {
                    messages = new SmsMessage[pdus.length];     //Has all the messages, which in our case is the newest single message.
                } else {
                    return;
                }
                for(int i=0; i<messages.length; i++){

                    //Create the SMS messages from the PDUs.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        messages[i] = SmsMessage.createFromPdu((byte[])pdus[i], format);    //The format is used only in Marshmallow(23) and above.
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    }

                    String msgBody = messages[i].getMessageBody();      //The SMS body text.
                    String code = Parser.parseString(msgBody);          //Get the code the message corresponds to.
                    switch (code) {
                        case Parser.UNMUTE:
                            //Unmute the device.
                            unmuteDevice();
                            break;
                        case Parser.UNKNOWN:
                            //Unknown command.
                            break;
                        case Parser.BAD_CREDENTIALS:
                            //Bad password.
                            break;
                    }
                }
            }catch(Exception e){
                Log.e("Exception caught",e.getMessage());
            }
        }
    }

    private void unmuteDevice(){

        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL); // Removes silent

        while(audioManager.getStreamVolume(AudioManager.STREAM_RING) < audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)){ // While volume isn't max.
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING, // Increase volume
                    AudioManager.ADJUST_RAISE,
                    AudioManager.FLAG_SHOW_UI);
        }

    }

    public SMSReceiver(){
    }

}
