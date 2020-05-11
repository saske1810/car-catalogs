package com.example.diplom1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class Second extends AppCompatActivity {

   private List<Car> cars = new ArrayList<>();

   private ListView listView;

   private CustomAdapter customAdapter;

   private RequestQueue queue;

    private void loadCars(int brand) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, HttpApiUtil.URL_API+"/cars/" + brand, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i = 0; i < response.length(); i++ ){
                                JSONObject car = response.getJSONObject(i);

                                cars.add(new Car(
                                        car.getInt("id"),
                                        car.getString("name"),
                                        car.getString("image")
                                ));

                                customAdapter = new Second.CustomAdapter(cars,Second.this);

                                listView.setAdapter(customAdapter);
                                Log.e("debuggggg",cars.toString());
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listview_sec);
        queue    = Volley.newRequestQueue(this);


        Bundle arguments = getIntent().getExtras();
        ItemsModel model = (ItemsModel) arguments.get("brand");

        this.loadCars(model.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                customAdapter.getFilter().filter(newText);

                return true;
            }
        });


        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.searchView){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<Car> itemsModelsl;
        private List<Car> itemsModelListFiltered;
        private Context context;

        public CustomAdapter(List<Car> itemsModelsl, Context context) {
            this.itemsModelsl           = itemsModelsl;
            this.itemsModelListFiltered = itemsModelsl;
            this.context                = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items,null);


            TextView names      = view.findViewById(R.id.name);
            ImageView imageView = view.findViewById(R.id.images);

            names.setText(itemsModelListFiltered.get(position).getName());

            URL url = null;
            try {
                url = new URL(HttpApiUtil.URL_IMAGE_STORAGE + "/car/" + itemsModelListFiltered.get(position).getImage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                imageView.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Second.this,ItemsPreviewActivity.class).putExtra("car", itemsModelListFiltered.get(position) ));
                }
            });

            return view;
        }



        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();

                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelsl.size();
                        filterResults.values = itemsModelsl;

                    }else{
                        List<Car> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for(Car itemsModel:itemsModelsl){
                            if(itemsModel.getName().contains(searchStr) || itemsModel.getName().contains(searchStr)){
                                resultsModel.add(itemsModel);

                            }
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;

                    }

                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (List<Car>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }

}