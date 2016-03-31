package com.now.live.livenow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class CreateEventFragment extends Fragment {

    private Event event;

    private EditText titleEdit;
    private EditText descriptionEdit;
    private EditText dateEdit;
    private EditText timeEdit;
    private EditText placeEdit;

    public CreateEventFragment(){
        //Empty Constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.create_event_fragment, null);

        //Init components
        titleEdit = (EditText) view.findViewById(R.id.title);
        descriptionEdit = (EditText) view.findViewById(R.id.description);
        dateEdit = (EditText) view.findViewById(R.id.date);
        timeEdit = (EditText) view.findViewById(R.id.time);
        placeEdit = (EditText) view.findViewById(R.id.place);

        event = new Event("", "", "", "", "");


        setFields();

        return view;
    }

    public void setFields() {
        if (StaticHelperClasses.checkNull(event.getTitle())) {
            titleEdit.setHint("TitleFoo");
        } else {
            titleEdit.setText(event.getTitle());
        }
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        getFields();
        return this.event;
    }

    public void getFields() {
        String title;
        String description;
        String date;
        String time;
        String place;

        title = titleEdit.getText().toString();
        description = descriptionEdit.getText().toString();
        date = dateEdit.getText().toString();
        time = timeEdit.getText().toString();
        place = placeEdit.getText().toString();


        event = new Event(
                title,
                description,
                date,
                time,
                place);
    }

}
