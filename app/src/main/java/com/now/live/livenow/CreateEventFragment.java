package com.now.live.livenow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CreateEventFragment extends Fragment {

    private Event event;

    private TextView titleView;

    public CreateEventFragment(){
        //Empty Constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.create_event_fragment, null);

        //Init components
        titleView = (TextView) view.findViewById(R.id.title);

        event = new Event(
                "djt",
                "Description Text",
                null,
                "16:00",
                "Location");


        setFields();

        return view;
    }

    public void setFields() {
        if (StaticHelperClasses.checkNull(event.getTitle())) {
            titleView.setHint("Title");
        } else {
            titleView.setText(event.getTitle());
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

        title = titleView.getText().toString();

        event = new Event(
                title,
                "Description Text",
                null,
                "16:00",
                "Location");
    }

}
