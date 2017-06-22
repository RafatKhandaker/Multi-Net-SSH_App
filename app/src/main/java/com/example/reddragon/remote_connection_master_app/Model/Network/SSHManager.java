package com.example.reddragon.remote_connection_master_app.Model.Network;

import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by RedDragon on 2/7/17.
 */

public class SSHManager extends AsyncTask<String, Integer, String> {

    private static final Logger LOGGER = Logger.getLogger(SSHManager.class.getName());
    private JSch jschSSHChannel;
    private String strUserName;
    private String strConnectionIP;
    private int intConnectionPort;
    private String strPassword;
    private Session sesConnection;
    private int intTimeOut;

    private String username;
    private String password;
    private String connectionIP;
    private String knownHostsFileName;
    private String connectKey;

    private void doCommonConstructorActions(String userName,
                                            String password,
                                            String connectionIP,
                                            String knownHostsFileName,
                                            String connectKey)
    {
        jschSSHChannel = new JSch();

        try
        {
            jschSSHChannel.setKnownHosts(knownHostsFileName);
            jschSSHChannel.addIdentity(connectKey);
        }
        catch(JSchException jschX)
        {
            logError(jschX.getMessage());
        }

        strUserName = userName;
        strPassword = password;
        strConnectionIP = connectionIP;

    }

    public SSHManager(String userName, String password,
                      String connectionIP, String knownHostsFileName, String connectKey, int port)
    {

        this.username = userName;
        this.password = password;
        this.connectionIP = connectionIP;
        this.knownHostsFileName = knownHostsFileName;
        this.connectKey = connectKey;

        doCommonConstructorActions(userName, password,
                connectionIP, knownHostsFileName, connectKey);
        intConnectionPort = port;
        intTimeOut = 60000;
    }

    public SSHManager(String userName, String password, String connectionIP,
                      String knownHostsFileName, int connectionPort, String connectKey)
    {
        doCommonConstructorActions(userName, password, connectionIP,
                knownHostsFileName, connectKey);
        intConnectionPort = connectionPort;
        intTimeOut = 60000;
    }

    public SSHManager(String userName, String password, String connectionIP,
                      String knownHostsFileName, int connectionPort, int timeOutMilliseconds, String connectKey)
    {
        doCommonConstructorActions(userName, password, connectionIP,
                knownHostsFileName, connectKey);
        intConnectionPort = connectionPort;
        intTimeOut = timeOutMilliseconds;
    }

    public String connect()
    {
        String errorMessage = null;

        try
        {
            sesConnection = jschSSHChannel.getSession(strUserName,
                    strConnectionIP, intConnectionPort);
            sesConnection.setPassword(strPassword);
            // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
            // sesConnection.setConfig("StrictHostKeyChecking", "no");
            sesConnection.connect(intTimeOut);
        }
        catch(JSchException jschX)
        {
            errorMessage = jschX.getMessage();
        }

        return errorMessage;
    }

    private String logError(String errorMessage)
    {
        if(errorMessage != null)
        {
            LOGGER.log(Level.SEVERE, "{0}:{1} - {2}",
                    new Object[]{strConnectionIP, intConnectionPort, errorMessage});
        }

        return errorMessage;
    }

    private String logWarning(String warnMessage)
    {
        if(warnMessage != null)
        {
            LOGGER.log(Level.WARNING, "{0}:{1} - {2}",
                    new Object[]{strConnectionIP, intConnectionPort, warnMessage});
        }

        return warnMessage;
    }

    public String sendCommand(String command)
    {
        StringBuilder outputBuffer = new StringBuilder();

        try
        {
            Channel channel = sesConnection.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();

            while(readByte != 0xffffffff)
            {
                outputBuffer.append((char)readByte);
                readByte = commandOutput.read();
            }

            channel.disconnect();
        }
        catch(IOException ioX)
        {
            logWarning(ioX.getMessage());
            return null;
        }
        catch(JSchException jschX)
        {
            logWarning(jschX.getMessage());
            return null;
        }

        return outputBuffer.toString();
    }

    public void close()
    {
        sesConnection.disconnect();
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
