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

public class UpdateTaskActivity extends AppCompatActivity {

    // поля
    private EditText taskName, taskDescription;
    private Button buttonUpdate, buttonDelete;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        // привязка разметки к полям
        taskName = findViewById(R.id.task_name);
        taskDescription = findViewById(R.id.task_description);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);

        // считывание данных из Intent
        Intent intent = getIntent();
        // вывод считанных данных в активность
        taskName.setText(intent.getStringExtra("name"));
        taskDescription.setText(intent.getStringExtra("description"));
        id = intent.getStringExtra("id");

        // обработка нажатия кнопки
        buttonUpdate.setOnClickListener(listener);
        buttonDelete.setOnClickListener(listener);
    }

    // определение слушателя
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // если исправленный текст не пустой, то обновление записи в БД
            if (!TextUtils.isEmpty(taskName.getText().toString()) && !TextUtils.isEmpty(taskDescription.getText().toString())) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext()); // создание объекта БД в текущей активности

                switch (view.getId()) {
                    case R.id.button_update:
                        String TN = taskName.getText().toString();
                        String TD = taskDescription.getText().toString();
                        dataBaseHelper.updateNotes(TN, TD,id);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                    case R.id.button_delete:
                        // удаление задачи
                        dataBaseHelper.deleteSingleItem(id); // удаление записи БД по id
                        break;
                }

                startActivity(new Intent(getApplicationContext(), MainActivity.class)); // переключение обратно в активность демонстрации всех записей

                finish(); // при нажатии на кнопку назад действие уничтожается и проиходит переход обратно в активность MainActivity
            } else { // иначе вывод сообщения на экран смартфона
                Toast.makeText(getApplicationContext(), "Данные не обновились", Toast.LENGTH_SHORT).show();
            }
        }
    };
}