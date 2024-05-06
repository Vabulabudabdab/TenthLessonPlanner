package com.msaggik.tenthlessonplanner.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.msaggik.tenthlessonplanner.R;
import com.msaggik.tenthlessonplanner.model.Task;
import com.msaggik.tenthlessonplanner.view.UpdateTaskActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{


    // поля адаптера
    private Context context;
    private final LayoutInflater inflater; // поле для трансформации layout-файла во View-элемент
    private final List<Task> tasks; // поле коллекции контейнера для хранения данных (объектов класса Task)

    // конструктор адаптера
    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.tasks = tasks;
    }

    // метод onCreateViewHolder() возвращает объект ViewHolder(), который будет хранить данные по одному объекту Task
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_item, parent, false); // трансформация layout-файла во View-элемент
        return new ViewHolder(view);
    }

    // метод onBindViewHolder() выполняет привязку объекта ViewHolder к объекту Task по определенной позиции
    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Task task = tasks.get(position);
        holder.nameView.setText(task.getName());
        holder.descriptionView.setText(task.getDescription());

        // обработаем нажатие на контейнер task_item.xml
        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // задание переключения на новый экран
                Intent intent = new Intent(context, UpdateTaskActivity.class);
                // передача данных в новую активити
                intent.putExtra("name", tasks.get(position).getName());
                intent.putExtra("description", tasks.get(position).getDescription());
                intent.putExtra("id", tasks.get(position).getId());
                // старт перехода
                context.startActivity(intent);
            }
        });
    }

    // метод getItemCount() возвращает количество объектов в списке
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // созданный статический класс ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // поля представления
        TextView nameView, descriptionView;
        ConstraintLayout layoutView;

        // конструктор класса ViewHolder с помощью которого мы связываем поля и представление task_item.xml
        ViewHolder(@NonNull View view) {
            super(view);
            nameView = view.findViewById(R.id.task_name);
            descriptionView = view.findViewById(R.id.task_description);
            layoutView = view.findViewById(R.id.task_layout);
        }
    }
}
