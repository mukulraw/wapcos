package com.nsez.wapcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class profile extends Fragment {

    TextView name , email , phone , address , company , logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile , container , false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        company = view.findViewById(R.id.company);
        logout = view.findViewById(R.id.logout);

        name.setText(SharePreferenceUtils.getInstance().getString("name"));
        email.setText(SharePreferenceUtils.getInstance().getString("email"));
        phone.setText(SharePreferenceUtils.getInstance().getString("mobile"));
        address.setText(SharePreferenceUtils.getInstance().getString("address"));
        company.setText(SharePreferenceUtils.getInstance().getString("company"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharePreferenceUtils.getInstance().deletePref();

                Intent intent = new Intent(getContext() , Splash.class);
                startActivity(intent);
                getActivity().finishAffinity();

            }
        });

        return view;
    }
}
