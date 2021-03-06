package com.flaviumircia.aquatrouble;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        TextView eula = v.findViewById(R.id.tosHyperlink);

        Button continueButton = v.findViewById(R.id.continueButton);
        CheckBox checkBox = v.findViewById(R.id.tosCheckBox);
        checkIfAccepted(checkBox, continueButton);
        //TODO: make eula legal
        eula.setOnClickListener(view -> startActivity(new Intent(getActivity(),Eula.class)));
        return v;
    }

    private void checkIfAccepted(CheckBox checkBox, Button continueButton) {
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                continueButton.setOnClickListener(view -> { // if the checkbox is checked and the continue button is pushed
                    startActivity(new Intent(getActivity(), MainMap.class)); // start new activity
                    saveStatus(); // save the check button status
                });
            }
        });

        if (!checkBox.isChecked()) { // if the checkbox wasn't checked
            continueButton.setOnClickListener(view -> // click on the button and show toast message
                    Toast.makeText(getActivity(),
                            "Pentru a putea continua trebuie sa acceptati termenii si conditiile",
                            Toast.LENGTH_SHORT).show());
        }
    }

    //save status method
    private void saveStatus() {   //always true because it is called inside the isChecked method
        boolean hasAccepted = true;

        UserModel userModel = new UserModel();

        userModel.setHasAccepted(hasAccepted);//setting status for object

        Database.getDatabase(getActivity().getApplicationContext()).getDao().insertAllData(userModel); // pushing the object to the database

        Toast.makeText(getActivity(), "Ai acceptat termenii si conditiile noastre!", Toast.LENGTH_SHORT).show(); // showing toast
    }
}
