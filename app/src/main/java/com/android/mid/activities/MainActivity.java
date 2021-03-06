package com.android.mid.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.mid.R;
import com.android.mid.standard.Batt5New;
import com.android.mid.standard.EcoBar;
import com.android.mid.standard.Ending;
import com.android.mid.standard.Fuel;
import com.android.mid.standard.Opening;
import com.android.mid.standard.Warning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by nachanok.boo on 11/23/2015.
 */
public class MainActivity extends Activity {
    public static final String SCREEN_BATT1 = "BATT 1";
    public static final String SCREEN_BATT2 = "BATT 2";
    public static final String SCREEN_BATT3 = "BATT 3";
    public static final String SCREEN_BATT4 = "BATT 4";
    public static final String SCREEN_BATT5 = "BATT 5";
    public static final String SCREEN_OPENING = "OPENING";
    public static final String SCREEN_ENDING = "ENDING";
    public static final String SCREEN_FUEL = "FUEL";
    public static final String SCREEN_WARNING = "WARNING";
    public static final String SCREEN_ECO_BAR = "ECO";

    private String screen_name;

    private int screen = 0;
    private Fragment fragment;
    String message = "";
    ServerSocket serverSocket;
    Thread socketServerThread;
    private TextView info;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            screen = screen + 1;
            switch (screen) {

                case 1: fragment = Opening.newInstance(); break;
                case 2: fragment = Ending.newInstance(); break;
                case 3: fragment = Fuel.newInstance(); break;

                default: screen = 0;
            }

            getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();

            new Handler().postDelayed(runnable, 6000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d("screen", "on create");
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        info = (TextView) findViewById(R.id.textInfo);

        socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
        //screenChange(SCREEN_OPENING);

    }


    @Override
    protected void onPause() {
        super.onPause();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.d("screen", "on pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if(screen_name != null){
            screenChange(screen_name);
        }
        Log.d("screen","on resume");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("currentScreen", screen_name);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        screen_name = savedInstanceState.getString("currentScreen");
    }

    private void screenChange(String screen){
        screen_name = screen;

        Log.d("screen","in screen change: "+ screen_name);
        switch (screen){
            case SCREEN_BATT1:
                getFragmentManager().beginTransaction().replace(android.R.id.content, EcoBar.newInstance(1)).commit();
                break;
            case SCREEN_BATT2:
                getFragmentManager().beginTransaction().replace(android.R.id.content, EcoBar.newInstance(2)).commit();
                break;
            case SCREEN_BATT3:
                getFragmentManager().beginTransaction().replace(android.R.id.content, EcoBar.newInstance(0)).commit();
                break;
            case SCREEN_BATT4:
                getFragmentManager().beginTransaction().replace(android.R.id.content, EcoBar.newInstance(4)).commit();
                break;
            case SCREEN_BATT5:
                //TODO: NEW BATT5 DESIGN
                getFragmentManager().beginTransaction().replace(android.R.id.content, Batt5New.newInstance()).commit();
                break;
            case SCREEN_OPENING:
                getFragmentManager().beginTransaction().replace(android.R.id.content,Opening.newInstance()).commit();
                break;
            case SCREEN_ENDING:
                getFragmentManager().beginTransaction().replace(android.R.id.content, Ending.newInstance()).commit();
                break;
            case SCREEN_FUEL:
                getFragmentManager().beginTransaction().replace(android.R.id.content, Fuel.newInstance()).commit();
                break;
            case SCREEN_WARNING:
                getFragmentManager().beginTransaction().replace(android.R.id.content, Warning.newInstance()).commit();
                break;
            case SCREEN_ECO_BAR:
                getFragmentManager().beginTransaction().replace(android.R.id.content, EcoBar.newInstance(0)).commit();
                break;
        }

    };
    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 8080;
        int count = 0;
        String btnName ="nothing...";
        SocketServerReplyThread socketServerReplyThread;
        @Override
        public void run() {

            try {
                serverSocket = new ServerSocket(); // <-- create an unbound socket first
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(SocketServerPORT)); // <-- now bind it

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        info.setText("I'm waiting here: "
                                + serverSocket.getLocalPort() + "\n" + getIpAddress());
                    }
                });

                while (true) {
                    Socket socket = serverSocket.accept();

                    //TODO Get input stream from client
                    InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader reader = new BufferedReader(streamReader);
                    btnName = reader.readLine();
                    socket.shutdownInput();
                    //--
                    count++;
                    message = "#" + count + " from " + socket.getInetAddress()
                            + ":" + socket.getPort() + "\n";
                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            String send = btnName;
                            screenChange(send);

                        }
                    });
                    socketServerReplyThread = new SocketServerReplyThread(socket,btnName);
                    socketServerReplyThread.run();

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;
        int cnt;
        String response;
        SocketServerReplyThread(Socket socket, String res) {
            hostThreadSocket = socket;
            response  = res;
        }

        @Override
        public void run() {
            OutputStream outputStream;
            String msgReply = response;

            try {
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgReply);
                Log.d("Server", "Reply success: " + msgReply);
                printStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message += "Something wrong! " + e.toString() + "\n";
            }finally{
                if(hostThreadSocket != null){
                    try {
                        hostThreadSocket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }


}
