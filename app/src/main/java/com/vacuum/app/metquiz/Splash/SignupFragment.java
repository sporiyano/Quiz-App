package com.vacuum.app.metquiz.Splash;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vacuum.app.metquiz.R;
import com.vacuum.app.metquiz.Utils.RegisterAPI;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupFragment extends Fragment implements View.OnClickListener{

    final static String SIGNUP_FRAGMENT_TAG = "SIGNUP_FRAGMENT_TAG";
    private EditText cardnumber,email,password,fname,lname,phone,login_cardnumber,login_password;
    Button buttonRegister;
    String ROOT_URL = "http://192.168.1.5/";
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_layout, container, false);
        mContext = this.getActivity();
        cardnumber =  view.findViewById(R.id.card_number);
        email =  view.findViewById(R.id.email);
        password =  view.findViewById(R.id.password);
        fname =  view.findViewById(R.id.fname);
        lname =  view.findViewById(R.id.lname);
        phone =  view.findViewById(R.id.phone);
        buttonRegister =  view.findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
        return view;
    }


    private void insertUser() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        api.insertUser(
                cardnumber.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                fname.getText().toString(),
                lname.getText().toString(),
                phone.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()) {
                    String responsse ;
                    try {
                        responsse  = response.body().string();
                        //System.out.println("====================================================");
                        //System.out.println(responsse);
                        Log.e("TAG", responsse.toString());
                        Toast.makeText(mContext,responsse, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "insertUser():: Unable to submit post to API.");
            }
        });
    }

    private void validateFields() {
        if (cardnumber.getText().length() == 0) {
            cardnumber.setError("Empty Field");
        }else if (email.getText().length() == 0){
            email.setError("Empty Field");
        }else {
            insertUser();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                validateFields();

                break;
        }
    }



}