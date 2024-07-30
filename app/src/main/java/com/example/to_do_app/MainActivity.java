package com.example.to_do_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private EditText editTextTask;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTask = findViewById(R.id.editTextTask);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasks, this);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);
        recyclerViewTasks.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


        findViewById(R.id.buttonAddTask).setOnClickListener(v -> addTask());
    }

    private void addTask() {
        String taskDescription = editTextTask.getText().toString();
        if (!taskDescription.isEmpty()) {
            Task newTask = new Task(taskDescription);
            tasks.add(newTask);
            taskAdapter.notifyItemInserted(tasks.size() - 1);
            editTextTask.setText("");
        } else {
            Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEditClick(Task task) {
        showEditTaskDialog(task);
    }

    @Override
    public void onDeleteClick(Task task) {
        int position = tasks.indexOf(task);
        if (position != -1) {
            tasks.remove(position);
            taskAdapter.notifyItemRemoved(position);
        }
    }

    private void showEditTaskDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_task, null);
        builder.setView(dialogView);

        EditText editTextEditTask = dialogView.findViewById(R.id.editTextEditTask);
        editTextEditTask.setText(task.getDescription());

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newTaskDescription = editTextEditTask.getText().toString();
            if (!newTaskDescription.isEmpty()) {
                task.setDescription(newTaskDescription);
                int position = tasks.indexOf(task);
                taskAdapter.notifyItemChanged(position);
            } else {
                Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
