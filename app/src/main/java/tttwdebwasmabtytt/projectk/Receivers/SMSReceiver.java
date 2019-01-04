package tttwdebwasmabtytt.projectk.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import tttwdebwasmabtytt.projectk.utilities.Parser;

public class SMSReceiver extends BroadcastReceiver {

    // Used to parse the sms
    Parser parser;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(parser==null){
            parser = new Parser();
        }

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           // Get the SMS message passed in
            SmsMessage[] msgs = null;
            String msg_from = null, msgBody = null;
            if (bundle != null){
                // Retrieve the SMS message received
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                         msgBody = msgs[i].getMessageBody();
                    }

                    // parse the sms message
                    if(parser.parseString(msgBody)){
                        Toast.makeText(context, "Hey, we got 123!", Toast.LENGTH_LONG);
                    }

                }catch(Exception e){
                    Log.d("Exception caught",e.getMessage());
                }
            }
        }

    }
}
