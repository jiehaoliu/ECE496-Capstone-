package com.tess.scarlett.tess_v5;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FrameLayout recentScans;
    private FrameLayout purchased;
    private FrameLayout favourites;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((MainActivity)getActivity()).hideUpButton();
//        final FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){
//            @Override
//            public void onBackStackChanged() {
//                // enable Up button only  if there are entries on the backstack
//                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                    fragmentManager.popBackStack();
//                    ((MainActivity)getActivity()).hideUpButton();
//                }
//            }
//        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recentScans = view.findViewById(R.id.recentScans);
        purchased = view.findViewById(R.id.purchased);
        favourites = view.findViewById(R.id.favourites);

        recentScans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment selectedFragment = RecentScansFragment.newInstance("","");
                FragmentTransaction transaction = getFragmentManager() .beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }

        });

        purchased.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment selectedFragment = PurchasedFragment.newInstance("","");
                FragmentTransaction transaction = getFragmentManager() .beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }

        });

        favourites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment selectedFragment = FavouritesFragment.newInstance("","");
                FragmentTransaction transaction = getFragmentManager() .beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }

        });
        return view;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
