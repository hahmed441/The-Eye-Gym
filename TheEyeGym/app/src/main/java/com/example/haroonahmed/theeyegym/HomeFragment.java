package com.example.haroonahmed.theeyegym;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
Button button;

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View myView = inflater.inflate(R.layout.fragment_home, container, false);
    button = (Button) myView.findViewById(R.id.button);
    button.setOnClickListener(this);
    return myView;
}

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Animation.class);
        startActivity(intent);
    }

}
