package com.example.olmazsa_soverim3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.olmazsa_soverim3.MainActivity;
import com.example.olmazsa_soverim3.R;
import com.example.olmazsa_soverim3.databinding.FragmentSignupPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentSignupPage extends Fragment {
    private FragmentSignupPageBinding tasarim;
    private String txtEmail, txtSifre, txtSifre2;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tasarim = FragmentSignupPageBinding.inflate(getLayoutInflater(), container, false);
        passwordVisible();
        signUpButton();
        login();
        mAuth = FirebaseAuth.getInstance();
        return tasarim.getRoot();
    }

    public void passwordVisible() {
        tasarim.buttonPasswordVisible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasarim.editTextPassword1.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    tasarim.editTextPassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    tasarim.buttonPasswordVisible1.setBackgroundResource(R.drawable.baseline_visibility_off_24);
                } else {
                    tasarim.editTextPassword1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    tasarim.buttonPasswordVisible1.setBackgroundResource(R.drawable.baseline_visibility_24);
                }
            }
        });

        tasarim.buttonPasswordVisible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasarim.editTextPassword2.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    tasarim.editTextPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    tasarim.buttonPasswordVisible2.setBackgroundResource(R.drawable.baseline_visibility_off_24);
                } else {
                    tasarim.editTextPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    tasarim.buttonPasswordVisible2.setBackgroundResource(R.drawable.baseline_visibility_24);
                }
            }
        });
    }

    public void login(){
        tasarim.textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new FragmentSigninPage();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.layout);
                if (currentFragment != null){
                    fragmentManager.beginTransaction().remove(currentFragment).commit();
                }
                fragmentManager.beginTransaction().add(R.id.layout, newFragment).commit();
            }
        });

        tasarim.getRoot().setFocusableInTouchMode(true);
        tasarim.getRoot().requestFocus();
        tasarim.getRoot().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                Fragment newFragment = new FragmentSigninPage();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.layout);
                if (currentFragment != null){
                    fragmentManager.beginTransaction().remove(currentFragment).commit();
                }
                fragmentManager.beginTransaction().add(R.id.layout, newFragment).commit();
                return true;
            } return false;
        });
    }

    public void signUpButton(){
        tasarim.buttonKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void signUp() {
        txtEmail = tasarim.editTextEmail2.getText().toString();
        txtSifre = tasarim.editTextPassword1.getText().toString();
        txtSifre2 = tasarim.editTextPassword2.getText().toString();

        if (txtSifre.equals(txtSifre2)){
            if(!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtSifre)){
                mAuth.createUserWithEmailAndPassword(txtEmail,txtSifre)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Kayıt Olma İşleminiz Gerçekleşmiştir",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(getActivity(),"Lütfen Gerekli Tüm Alanları Doldurunuz",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Şifreler Farklı Olamaz", Toast.LENGTH_SHORT).show();
        }
    }
}
