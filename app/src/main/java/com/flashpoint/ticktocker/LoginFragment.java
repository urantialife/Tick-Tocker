package com.flashpoint.ticktocker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import com.google.firebase.auth.AuthResult;

public class LoginFragment extends Fragment {

    private Button LoginButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);
        LoginButton = (Button) view.findViewById(R.id.buttonlogin);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        final Bundle bundle = new Bundle();
        LoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editTextEmail.getText().toString().trim();
                bundle.putString("user", email);
                final String password = editTextPassword.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FragmentActivity activity = getActivity();
                                MainActivity mainActivity = (MainActivity) activity;
                                mainActivity.showFragment(new CreateEventFragment());
                            }
                        })
                        .addOnFailureListener(getActivity(), new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                        .setTitle("Invalid credentials")
                                        .setMessage("Please check your email and password")
                                        .setPositiveButton(android.R.string.ok, null);

                                builder.show();

                            }
                        });
            }
        });
        setArguments(bundle);
        return view;
    }
}

