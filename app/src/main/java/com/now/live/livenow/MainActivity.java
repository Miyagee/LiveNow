package com.now.live.livenow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.now.live.livenow.swipecard.FlingCardListener;
import com.now.live.livenow.swipecard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {


    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Event> al;
    private SwipeFlingAdapterView flingContainer;

    Firebase ref;

    public static void removeBackground() {


        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ref = new Firebase("https://live-now.firebaseio.com/");

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        al = new ArrayList<>();
        al.add(new Event(
                "Tea & Cake",
                "Fancy a bit of afternoon tea?",
                "22/03/16",
                "16:00",
                "Diggs"));
        al.add(new Event("Bowling",
                "A game of bowling for the whole family",
                "31/03/16",
                "18:00",
                "Bowling Land"));
        al.add(new Event("Singing",
                "Choir practice for beginners",
                "06/04/16",
                "10:00",
                "Nidaros Domen"));
        al.add(new Event("Fruit Picking",
                "Collect wild friuts with me :)",
                "20/04/16",
                "14:00",
                "Bymarka"));
        al.add(new Event("Programming",
                "I can teach you Python programming.",
                "01/05/2016",
                "09:00",
                "NTNU"));

        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the right!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView eventTitle;
        public TextView eventText;
        public TextView eventDate;
        public TextView eventTime;
        public TextView eventPlace;
        public ImageView cardAvatar;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Event> parkingList;
        public Context context;

        private MyAppAdapter(List<Event> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.eventTitle = (TextView) rowView.findViewById(R.id.title);
                viewHolder.eventText = (TextView) rowView.findViewById(R.id.description);
                viewHolder.eventTime = (TextView) rowView.findViewById(R.id.time);
                viewHolder.eventDate = (TextView) rowView.findViewById(R.id.date);
                viewHolder.eventPlace = (TextView) rowView.findViewById(R.id.place);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardAvatar = (ImageView) rowView.findViewById(R.id.cardAvatar);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.eventText.setText(parkingList.get(position).getDescription() + "");
            viewHolder.eventTitle.setText(parkingList.get(position).getTitle() + "");
            viewHolder.eventTime.setText("Time: " + parkingList.get(position).getTime() + "");
            viewHolder.eventDate.setText("Date: " + parkingList.get(position).getDate() + "");
            viewHolder.eventPlace.setText("Location: " + parkingList.get(position).getPlace());

            return rowView;
        }
    }

    //Log out button
    public void logOut(View view){
        ref.unauth();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



}
