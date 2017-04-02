package com.example.reddragon.remote_connection_master_app.Model.Network;

import android.os.AsyncTask;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * Created by RedDragon on 2/7/17.
 */

public class SSHManager extends AsyncTask<String, Integer, String> {

        String asd;
        @Override
        protected String doInBackground(String... arg0) {

            JSch jsch = new JSch();
            Properties props = new Properties();
            props.put("StrictHostKeyChecking", "no");

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("compression.s2c", "zlib,none");
            config.put("compression.c2s", "zlib,none");

            Session session;
            try {
                session = jsch.getSession("user", "x.x.x.x",22);
                session.setConfig(config);
                session.setPassword("xxxxxxx");
                session.connect();
            } catch (JSchException e) {
                asd = "NOT_Executed";
                System.out.println("NOT_executed");
                e.printStackTrace();
                return "NOT_Executed";
            }



            CharSequence text = "Connected to Pi";
            int duration = android.widget.Toast.LENGTH_SHORT;
            System.out.println("executed");
            asd = "executed";
            return "Executed";

            //return null;
        }

        @Override
        protected void onPostExecute(String abc){
        }

}
