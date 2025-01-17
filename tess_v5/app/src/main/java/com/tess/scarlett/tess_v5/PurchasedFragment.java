package com.tess.scarlett.tess_v5;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PurchasedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PurchasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchasedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PurchasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchasedFragment newInstance(String param1, String param2) {
        PurchasedFragment fragment = new PurchasedFragment();
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
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack(null);
        ((MainActivity)getActivity()).showUpButton();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_purchased, container, false);

        final File purchase_file = new File(getContext().getFilesDir() + "/map.ser");

        try {
            FileInputStream fis = new FileInputStream(purchase_file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map existing_map = (Map) ois.readObject();
            if (existing_map.size()==0){
                RelativeLayout layout = view.findViewById(R.id.purchased);
                layout.setVisibility(View.VISIBLE);
                layout = view.findViewById(R.id.purchasedResults);
                layout.setVisibility(View.GONE);
            }
            else{
                TextView textview = view.findViewById(R.id.purchased_title);
                textview.setPaintFlags(textview.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
                RelativeLayout layout = view.findViewById(R.id.purchased);
                layout.setVisibility(View.GONE);
                layout = view.findViewById(R.id.purchasedResults);
                layout.setVisibility(View.VISIBLE);
                LinearLayout layer= view.findViewById(R.id.purchased_items);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,10,0,0);
                //TextView text = view.findViewById(R.id.purchased_field);
                Iterator it = existing_map.keySet().iterator();
                int i =0;
                while (it.hasNext())
                {
                    i += 1;
                    Object key = it.next();
                    TextView textView = new TextView(getContext());
                    textView.setText(i+". "+String.valueOf(key).replaceAll("\\+", " ").toUpperCase());
                    textView.setPadding(30, 10, 0, 10);
                    textView.setTextSize(24);
                    textView.setTextColor(Color.parseColor("#F30F0F0F"));
                    textView.setLayoutParams(params);
                    textView.setBackgroundResource(R.drawable.result_style);
                    layer.addView(textView);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ImageButton clear_button = view.findViewById(R.id.clear_purchased);
        clear_button.setOnClickListener(new Button.OnClickListener() { // Then you should add add click listener for your button.
            @Override
            public void onClick(View v) {
                final AlertDialog dialog  = new AlertDialog.Builder(getContext())
                        .setTitle("Confirm")
                        .setMessage("Do you really want to clear your searching history?")
                        .setIcon(R.drawable.ic_warning)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                ObjectOutputStream oos;
                                FileOutputStream fos;
                                try {
                                    fos = new FileOutputStream(purchase_file);
                                    fos.close();//empty file
                                    fos = new FileOutputStream(purchase_file);
                                    oos = new ObjectOutputStream(fos);
                                    Map existing_map = new HashMap();
                                    oos.writeObject(existing_map);
                                    oos.close();
                                    fos.close();
                                    Fragment selectedFragment = PurchasedFragment.newInstance("","");
                                    FragmentTransaction transaction = getFragmentManager() .beginTransaction();
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                    transaction.commit();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }})
                        .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        }).create();

                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#96061a"));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#96061a"));
                    }
                });

                dialog.show();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
