package com.example.admin.myfirstapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.myfirstapp.Global.Globals;
import com.example.admin.myfirstapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooseExistingFlight extends Fragment {

    View view;
    Button btnBack,btnConfirm;
    TextView txtFlightNo,txtDeparture1,txtDeparture2,txtArrival1,txtArrival2;
    AddExistingFlightFragment existingFlightFragment;
    Globals g;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_choose_existing_flight, container, false);

        g=Globals.getInstance();

        btnBack=view.findViewById(R.id.btnBack);
        btnConfirm=view.findViewById(R.id.btnConfirm);

        txtFlightNo=view.findViewById(R.id.txtFlightNoValue);

        txtDeparture1=view.findViewById(R.id.txtDepartureValue1);
        txtDeparture2=view.findViewById(R.id.txtDepartureValue2);

        txtArrival1=view.findViewById(R.id.txtArrivalValue1);
        txtArrival2=view.findViewById(R.id.txtArrivalValue2);
        existingFlightFragment=new AddExistingFlightFragment();


        try {
            JSONObject jFlightData=new JSONObject(new String(g.getAddExistingFlightJson()));

            if(jFlightData!=null)
            {
                txtFlightNo.setText(jFlightData.getString("Operator") +" "+jFlightData.getString("FlightNumber"));

                txtDeparture1.setText(jFlightData.getString("Source"));
                txtArrival1.setText(jFlightData.getString("Destination"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                SimpleDateFormat outputFormat=new SimpleDateFormat("MMM dd, yyyy hh:mm a");
                try {
                    Date date = format.parse(jFlightData.getString("SourceTime"));

                    txtDeparture2.setText(outputFormat.format(date));

                    Date date2 = format.parse(jFlightData.getString("DestinationTime"));

                    txtArrival2.setText(outputFormat.format(date2));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, existingFlightFragment);
                fragmentTransaction.detach(existingFlightFragment);
                fragmentTransaction.attach(existingFlightFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               