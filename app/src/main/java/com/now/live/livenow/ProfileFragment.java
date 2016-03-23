package com.now.live.livenow;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER = "user";

    private User user;

    //User data display
    private TextView nameView;
    private TextView genderView;
    private TextView descriptionView;
    private TextView ageView;
    private TextView distanceView;
    private SeekBar distanceBar;



    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        //args.putParcelable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Setting stuff for interaction
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_profile,null);
        new DownloadImageTask((ImageView) view.findViewById(R.id.picture_profile))
                .execute(user.getPicture());

        //Init textViews
        nameView = (TextView) view.findViewById(R.id.name_profile);
        genderView = (TextView) view.findViewById(R.id.gender_profile);
        ageView = (TextView) view.findViewById(R.id.age_profile);
        descriptionView = (TextView) view.findViewById(R.id.description_profile);
        distanceView = (TextView) view.findViewById(R.id.distance_number_profile);
        distanceBar = (SeekBar) view.findViewById(R.id.distance_profile);

        distanceBar.setEnabled(false);
        //Setting fields
        setFields();

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setFields(){
        String distanceConvert = Integer.toString(user.getDiscoverRange()) + " km";

        nameView.setText(user.getName());


        genderView.setText(user.getGender());

        descriptionView.setText(user.getDescription());

        //TODO age and stuff needs to be changed and calculated
        ageView.setText("20");

        //Distance
        distanceView.setText(distanceConvert);
        distanceBar.setProgress(user.getDiscoverRange());
    }


}
