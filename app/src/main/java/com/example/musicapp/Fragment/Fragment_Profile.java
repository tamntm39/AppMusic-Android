package com.example.musicapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musicapp.Activity.MainActivity;
import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.ui.Utils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Fragment_Profile extends Fragment {
    TextView tvUserNameC;
    ImageView imageView;
    EditText edLink,edAvatar,edCasi,edName;
    private final Gson gson = new Gson();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        //Infalte the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        tvUserNameC = view.findViewById(R.id.tvUserName);
        imageView= view.findViewById(R.id.imageView);
        edName=view.findViewById(R.id.edName);
        edCasi=view.findViewById(R.id.edCasi);
        edAvatar=view.findViewById(R.id.edAvatar);
        edLink=view.findViewById(R.id.edLink);
        //
        SharedPreferences shareget = getActivity().getSharedPreferences(Utils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        String userPref = shareget.getString(Utils.KEY_USER, null);

        User user = gson.fromJson(userPref , User.class);

        if(user == null)
        {
            tvUserNameC.setText("Không có dữ liệu");
        }
        else
        {
            String info = "User name : " + user.getUserName() + "\n";
            info +="Email: " + user.getEmail() + "\n";
            tvUserNameC.setText(info);
        }
        return view;
    }
    private void onClickPushData(){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("baihatyeuthich");
//        myRef.setValue(Integer.parseInt(edName.getText().toString().trim())), new DatabaseReference.CompletionListener() {
//
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity.class, "data push success", Toast.LENGTH_SHORT).show();
//             }
//        };

    }
}
