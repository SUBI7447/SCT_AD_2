package com.example.to_do_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private OnTaskClickListener listener;

    public TaskAdapter(List<Task> tasks, OnTaskClickListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTaskDescription.setText(task.getDescription());

        holder.buttonEditTask.setOnClickListener(v -> listener.onEditClick(task));
        holder.buttonDeleteTask.setOnClickListener(v -> listener.onDeleteClick(task));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public interface OnTaskClickListener {
        void onEditClick(Task task);
        void onDeleteClick(Task task);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTaskDescription;
        Button buttonEditTask;
        Button buttonDeleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskDescription = itemView.findViewById(R.id.textViewTaskDescription);
            buttonEditTask = itemView.findViewById(R.id.buttonEditTask);
            buttonDeleteTask = itemView.findViewById(R.id.buttonDeleteTask);
        }
    }
}
