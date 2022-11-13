package com.example.todo;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private boolean subtitleVisible;
    public static final String KEY_EXTRA_TASK_ID = "task_id";
    public static final String KEY_SUBTITLE = "subtitle";

    public void updateSubtitle() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();
        int todoTasksCount = 0;
        for(Task task : tasks){
            if(!task.isDone()){
                todoTasksCount++;
            }
        }
        String subtitle = getString(R.string.subtitle_format, todoTasksCount);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setSubtitle(subtitleVisible ? subtitle : null);
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView;
        private TextView dateTextView;
        private Task task;
        private ImageView iconImageView;
        private CheckBox checkBoxView;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            nameTextView = itemView.findViewById(R.id.task_item_name);
            dateTextView = itemView.findViewById(R.id.task_item_date);
            iconImageView = itemView.findViewById(R.id.task_item_icon);
            checkBoxView = itemView.findViewById(R.id.task_item_checkbox);
        }

        public void bind(Task task){
            this.task = task;
            nameTextView.setText(task.getName());
            dateTextView.setText(task.getDate().toString());
            checkBoxView.setChecked(task.isDone());

            if(task.getCategory().equals(Category.HOME)){
                iconImageView.setImageResource(R.drawable.ic_home);
            }
            else{
                iconImageView.setImageResource(R.drawable.ic_study);
            }
        }
        public CheckBox getCheckBox(){
            return checkBoxView;
        }
        public TextView getNameTextView(){
            return nameTextView;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);

        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks){
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = tasks.get(position);
            CheckBox checkBox = holder.getCheckBox();
            TextView nameTextView = holder.getNameTextView();

            checkBox.setChecked(tasks.get(position).isDone());
            if(tasks.get(position).isDone())
                nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->{
                tasks.get(holder.getBindingAdapterPosition()).setDone(isChecked);
                if(isChecked)
                    nameTextView.setPaintFlags(nameTextView.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                else
                    nameTextView.setPaintFlags(nameTextView.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
            });
            holder.bind(task);

        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }

    private void updateView(){
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();

        if (adapter == null){
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        }
        else {
            adapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SUBTITLE, subtitleVisible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        subtitleVisible = savedInstanceState != null && savedInstanceState.getBoolean(KEY_SUBTITLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_menu, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        subtitleItem.setTitle(subtitleVisible ? R.string.hide_subtitle : R.string.show_subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_task:
                Task task = new Task();
                TaskStorage.getInstance().addTask(task);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(TaskListFragment.KEY_EXTRA_TASK_ID, task.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                subtitleVisible = !subtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
