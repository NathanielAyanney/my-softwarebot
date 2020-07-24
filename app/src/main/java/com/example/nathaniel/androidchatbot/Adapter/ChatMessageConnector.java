package com.example.nathaniel.androidchatbot.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nathaniel.androidchatbot.Model.ChatMessage;
import com.example.nathaniel.androidchatbot.R;

import java.util.List;

public class ChatMessageConnector extends ArrayAdapter<ChatMessage> {

    private static final int OUR_MESSAGE = 0;

    private static final int BOT_MESSAGE = 1;




    public ChatMessageConnector(@NonNull Context context, List<ChatMessage> data) {
        super(context, R.layout.my_user_layout,data);
    }

    @Override
    public int getItemViewType(int position) {


        ChatMessage item = getItem(position);

        if (item.isMyImage() && !item.isBotImage())   {
            return OUR_MESSAGE;

        }



        else {
            return BOT_MESSAGE;

        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View converttheView, @NonNull ViewGroup parent) {
        int theviewType = getItemViewType(position);

        if (theviewType == OUR_MESSAGE) {
            converttheView = LayoutInflater.from(getContext())
                    .inflate(R.layout.my_user_layout,parent, false);

            TextView textView = converttheView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }



        else if (theviewType == BOT_MESSAGE) {
            converttheView = LayoutInflater.from(getContext())
                    .inflate(R.layout.my_bot_layout,parent, false);



            TextView textView = converttheView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());

        }





        converttheView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked...", Toast.LENGTH_SHORT).show();
            }
        });




        return converttheView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}



