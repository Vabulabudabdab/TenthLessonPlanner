package com.msaggik.tenthlessonplanner.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.msaggik.tenthlessonplanner.R;
import com.msaggik.tenthlessonplanner.model.Task;
import com.msaggik.tenthlessonplanner.viewmodel.DataBaseHelper;
import com.msaggik.tenthlessonplanner.viewmodel.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // поля
    private Button button;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private DataBaseHelper dataBaseHelper;

    // создание коллекции контейнера для данных класса Task
    List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка разметки к полям
        recyclerView = findViewById(R.id.recyclerview);
        button = findViewById(R.id.button);

        // создание объекта работы с базой данных
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        // инициализация контейнера задач
        fetchAllTasks(tasks);

        // создадим адаптер и загрузим в него контейнер с данными
        taskAdapter = new TaskAdapter(this, tasks);

        // на recyclerView подадим адаптер
        recyclerView.setAdapter(taskAdapter);

        // обработка нажатия на кнопку
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // переключение на новую активность
                startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
            }
        });
    }

    // метод считывания из базы данных всех записей
    public void fetchAllTasks(List<Task> taskList){
        // чтение БД и запись данных в курсор
        Cursor cursor = dataBaseHelper.readTasks();

        if (cursor.getCount() == 0) { // если данные отсутствую, то вывод на экран об этом тоста
            Toast.makeText(this, "Задачи отсутствуют", Toast.LENGTH_SHORT).show();
        } else { // иначе помещение их в контейнер данных taskList
            while (cursor.moveToNext()){
                // помещение в контейнер taskList из курсора данных
                taskList.add(new Task(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }
}