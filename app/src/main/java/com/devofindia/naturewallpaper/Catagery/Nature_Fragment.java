package com.devofindia.naturewallpaper.Catagery;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devofindia.naturewallpaper.Adapters.WallpaperAdapter;
import com.devofindia.naturewallpaper.Modals.WallpaperModal;
import com.devofindia.naturewallpaper.R;
import com.devofindia.naturewallpaper.databinding.FragmentNatureBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Nature_Fragment extends Fragment {
    WallpaperAdapter wallpaperAdapter;
    ArrayList<WallpaperModal> wallpaperModalList;
    int page_number = 1;
    Boolean isScrolling = false;
    int currentItems, totalsItems, scrollOutItems;
    ConnectivityManager connectivityManager;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNatureBinding binding= FragmentNatureBinding.inflate(inflater , container, false);

        wallpaperModalList = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(wallpaperModalList, getContext());
        binding.recyclerview.setAdapter(wallpaperAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        binding.recyclerview.setLayoutManager(gridLayoutManager);

        connectivityManager= (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Make sure your internet is connected");

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info!=null && info.isConnected()){
            progressDialog.dismiss();
        }
        else {
            Toast.makeText(getContext(), "connected", Toast.LENGTH_SHORT).show();
            progressDialog.show();
        }

        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isScrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItems = gridLayoutManager.getChildCount();
                        scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
                        totalsItems = gridLayoutManager.getItemCount();

                        if (isScrolling && (currentItems+scrollOutItems==totalsItems)){
                            isScrolling = false;
                            fetchWallpaperApi();
                        }

                    }
                });
                fetchWallpaperApi();
                binding.swiperefresh.setRefreshing(false);
            }
        });
        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = gridLayoutManager.getChildCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
                totalsItems = gridLayoutManager.getItemCount();

                if (isScrolling && (currentItems+scrollOutItems==totalsItems)){
                    isScrolling = false;
                    fetchWallpaperApi();
                }

            }
        });
        fetchWallpaperApi();


       return binding.getRoot();
    }

    private void fetchWallpaperApi() {
        StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+page_number+"&per_page=80&query=nature",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        RetriveDataUsingJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> prams = new HashMap<>();
                prams.put("Authorization", "563492ad6f91700001000001056fbb52a56b4ee382619d4c0ce2b812");
                return prams;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    private void RetriveDataUsingJson(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("photos");
            int lenght = jsonArray.length();
            for (int i = 0; i<lenght; i++){
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("id");

                JSONObject photojsonObject = object.getJSONObject("src");
                String mediumUrl = photojsonObject.getString("medium");
                String orignalUrl = photojsonObject.getString("original");

                WallpaperModal modal = new WallpaperModal(id,orignalUrl,mediumUrl);
                wallpaperModalList.add(modal);
            }

            wallpaperAdapter.notifyDataSetChanged();
            page_number++;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}