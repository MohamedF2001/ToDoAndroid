package com.farid.todoliste;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farid.todoliste.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private RecyclerView mRVFish;
    private RecyclerViewAdapter mAdapter;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Recherch().execute();
    }

    private class Recherch extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                URL urls = new URL("https://todoappex.herokuapp.com/todo");
                HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
                conn.setReadTimeout(150000); //milliseconds
                conn.setConnectTimeout(15000); // milliseconds
                conn.setRequestMethod("GET");

                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            conn.getInputStream(), "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    result = sb.toString();
                } else {

                    return "error";
                }


            } catch (Exception e) {
                // System.out.println("exception in jsonparser class ........");
                e.printStackTrace();
                return "error";
            }

            return result;
        } // method ends

        @Override
        protected void onPostExecute(String result) {
            List<Data> data = new ArrayList<>();

            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Data fishData = new Data();
                    fishData.todoId = json_data.getString("_id");
                    fishData.todo = json_data.getString("todo");
                    fishData.frozen = json_data.getString("frozen");
                    fishData.todoDate = json_data.getString("creatDate");
                    fishData.todoDetail = json_data.getString("detail");

                    data.add(fishData);
                }

                mAdapter = new RecyclerViewAdapter(getContext(), data);
                mRVFish.setAdapter(mAdapter);
                mRVFish.setLayoutManager(new LinearLayoutManager(getContext()));

            } catch (JSONException e) {
                JSONObject json_ = null;
                String res = result;
                try {
                    json_ = new JSONObject(res);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                Toast.makeText(getContext(), "1 "+json_.getJSONObject("todos"), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "l'erreur : "+result, Toast.LENGTH_LONG).show();

                return;
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}