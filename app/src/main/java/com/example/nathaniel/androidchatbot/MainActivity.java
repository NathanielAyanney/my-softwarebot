package com.example.nathaniel.androidchatbot;

import android.Manifest;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.nathaniel.androidchatbot.Adapter.ChatMessageConnector;
import com.example.nathaniel.androidchatbot.Model.ChatMessage;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView softwarebotlistView;
    FloatingActionButton btnSendSoftwareQuery;
    EditText edtTextSoftwareMsg;
    ImageView FriendlyBot;

    private Bot bot;
    public static Chat chat;
    private ChatMessageConnector link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        softwarebotlistView = findViewById(R.id.softwarebotlistView);
        btnSendSoftwareQuery = findViewById(R.id.btnSendSoftwareQuery);
        edtTextSoftwareMsg = findViewById(R.id.edtTextSoftwareMsg);
        FriendlyBot = findViewById(R.id.FriendlyBot);

        link = new ChatMessageConnector(this,new ArrayList<ChatMessage>());
        softwarebotlistView.setAdapter(link);







        btnSendSoftwareQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = edtTextSoftwareMsg.getText().toString();

                String response = chat.multisentenceRespond(edtTextSoftwareMsg.getText().toString());




                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(MainActivity.this, "Please enter a software problem query", Toast.LENGTH_SHORT).show();
                    return;
                }


                sendSoftwareMessage(message);
                softwarebotresponce(response);

                edtTextSoftwareMsg.setText("");
                softwarebotlistView.setSelection(link.getCount() - 1);
            }
        });






        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    Toast.makeText(MainActivity.this, "Permission given for softwarebot...", Toast.LENGTH_SHORT).show();
                }
                if (report.isAnyPermissionPermanentlyDenied()) {
                    Toast.makeText(MainActivity.this, "All the permissions should be accepted for the softwarebot ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(MainActivity.this, "Softwarebot Error", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();





        boolean available = isSDCardAvailableOnDivice();

        AssetManager assets = getResources().getAssets();
        File fileName = new File(Environment.getExternalStorageDirectory().toString() + "/TBC/bots/softwarehelpbot");

        boolean makesoftwareFile = fileName.mkdirs();

        if (fileName.exists()) {


            try {
                for (String dir : assets.list("softwarehelpbot")) {

                    File subDir = new File(fileName.getPath() + "/" + dir);
                    boolean subDir_Check = subDir.mkdirs();

                    for (String file : assets.list("softwarehelpbot/" + dir))         {
                        File newFile = new File(fileName.getPath() + "/" + dir + "/" + file);

                        if (newFile.exists()) {
                            continue;
                        }

                        InputStream in;
                        OutputStream out;
                        String str;
                        in = assets.open("softwarehelpbot/" + dir + "/" + file);
                        out = new FileOutputStream(fileName.getPath() + "/" + dir + "/" + file);


                        copyFile(in,out);
                        in.close();
                        out.flush();
                        out.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/TBC";
        AIMLProcessor.extension = new PCAIMLProcessorExtension();

        bot = new Bot("softwarehelpbot",MagicStrings.root_path,"chatwithbot");
        chat = new Chat(bot);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;

        while ((read = in.read(buffer)) != -1)  {
            out.write(buffer,0,read);
        }
    }

    public static boolean isSDCardAvailableOnDivice() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)? true : false;
    }

    private void softwarebotresponce(String response) {
        ChatMessage chatMessage = new ChatMessage(false,false,response);
        link.add(chatMessage);
    }

    private void sendSoftwareMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(false,true,message);
        link.add(chatMessage);
    }
}
