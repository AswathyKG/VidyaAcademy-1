package com.vidhya.vidyaacademy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class F_Parent_Help extends Fragment {

    ImageView help_parent_img;
    TextView help_parent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_parent_help, container, false );

        help_parent_img = view.findViewById( R.id.help_parent_img );
        help_parent = view.findViewById( R.id.help_parent );

        return view;
    }

}



