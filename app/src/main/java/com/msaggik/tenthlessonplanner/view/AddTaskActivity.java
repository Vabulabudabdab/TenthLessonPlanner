package com.msaggik.tenthlessonplanner.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.msaggik.tenthlessonplanner.R;
import com.msaggik.tenthlessonplanner.viewmodel.DataBaseHelper;

public class AddTaskActivity extends AppCompatActivity {

    // поля
    private EditText taskName, taskDescription;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // привязка разметки к полям
        taskName = findViewById(R.id.task_name);
        taskDescription = findViewById(R.id.task_description);
        button = findViewById(R.id.button);

        // обработка нажатия кнопки
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // если введённый текст не пустой, то добавление записи в БД
                if (!TextUtils.isEmpty(taskName.getText().toString()) && !TextUtils.isEmpty(taskDescription.getText().toString())){

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext()); // создание объекта БД в текущей активности
                    dataBaseHelper.addTasks(taskName.getText().toString(), taskDescription.getText().toString()); // создание записи в БД

                    // создание намерения переключения активности обратно в главную активность
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); // переключение обратно в активность демонстрации всех задач
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // установления флага экономии ресурсов
                    startActivity(intent);

                    finish(); // при нажатии на кнопку назад действие уничтожается и проиходит переход обратно в активность MainActivity

                } else { // иначе сообщение о необходимости заполнения обоих полей
                    Toast.makeText(getApplicationContext(), "Необходимо заполнить оба поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}