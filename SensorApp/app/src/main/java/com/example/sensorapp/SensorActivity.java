package com.example.sensorapp;

import static com.example.sensorapp.SensorDetailsActivity.SENSOR_TYPE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    private boolean sensorCountVisible;

    private class SensorHolder extends RecyclerView.ViewHolder {
        private ImageView sensorIconImageView;
        private TextView sensorNameTextView;
        private Sensor sensor;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));

            sensorIconImageView = itemView.findViewById(R.id.sensor_icon);
            sensorNameTextView = itemView.findViewById(R.id.sensor_name);
        }

        public void bind(Sensor sensor){
            this.sensor = sensor;
            sensorNameTextView.setText(sensor.getName());

            if(sensor.getType() == Sensor.TYPE_LIGHT || sensor.getType() == Sensor.TYPE_GYROSCOPE){
                sensorNameTextView.setBackgroundColor(getResources().getColor(R.color.teal_200));
                sensorNameTextView.setOnClickListener(view -> {
                    Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
                    intent.putExtra(SENSOR_TYPE, sensor.getType());
                    startActivity(intent);
                });
            }
            if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                sensorNameTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
                sensorNameTextView.setOnClickListener(view -> {
                    Intent intent = new Intent(SensorActivity.this, LocationActivity.class);
                    startActivity(intent);
                });
            }

        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder> {
        private List<Sensor> sensors;

        public SensorAdapter(List<Sensor> sensors){
            this.sensors = sensors;
        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new SensorHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
            Sensor sensor = sensors.get(position);
            holder.bind(sensor);

        }

        @Override
        public int getItemCount() {
            return sensors.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        if(adapter == null) {
            adapter = new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        }
        else {
            adapter.notifyDataSetChanged();
        }

        for(Sensor sensor : sensorList){
            Log.d("SENSOR LOGS ",
                    "Producent: " + sensor.getVendor() +
                                "; Max. Wartość: " + sensor.getMaximumRange());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sensors_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.show_sensor_count:
                sensorCountVisible = !sensorCountVisible;
                String string = getString(R.string.sensors_count, sensorList.size());
                getSupportActionBar().setSubtitle(sensorCountVisible ? string : null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}