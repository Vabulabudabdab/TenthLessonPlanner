package com.msaggik.tenthlessonplanner.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    // поля базы данных
    private Context context; // поле текущего контекста

    private static final String DatabaseName = "MyPlanner"; // поле названия БД
    private static final int DatabaseVersion = 1; // поле версии БД

    private static final String tableName = "myTasks"; // поле названия таблицы в БД
    private static final String columnId = "id";  // поле колонки для идентификаторов задач в таблице в БД
    private static final String columnName = "name";  // поле колонки для названий задач в таблице в БД
    private static final String columnDescription = "description";  // поле колонки для описаний задач в таблице в БД

    // конструктор
    public DataBaseHelper(@Nullable Context context) {
        // задание параметров (контекст, название БД, курсор, версия БД)
        super(context, DatabaseName,null, DatabaseVersion);
        this.context = context;
    }

    // метод создания рабочей таблицы в БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        // определение запроса на создание таблицы базы данных
        String query = "CREATE TABLE " + tableName + " (" + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + columnName + " TEXT, " + columnDescription + " Text);";
        db.execSQL(query);
    }

    // метод обновления рабочей таблицы в БД
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // определение запроса на удаление таблицы базы данных
        db.execSQL("DROP TABLE IF EXISTS "+ tableName);
        // определение запроса на создание таблицы базы данных
        onCreate(db);
    }

    // методы работы с БД:

    // 1) добавить запись в БД
    public void addTasks(String name, String description) {

        /** с помощью getWritableDatabase() мы проверяем есть ли подключение к БД,
         * если есть то им пользуемся, если нет то создаём новое
         */
        SQLiteDatabase db = this.getWritableDatabase();

        /** нужно создать объект класса ContentValues для добавления и обновления существующих записей БД,
         * Данный объект представляет словарь, который содержит набор пар "ключ-значение"
         * Для добавления в этот словарь нового объекта применяется метод put
         */
        ContentValues cv = new ContentValues();

        cv.put(columnName,name); // добавление заголовка записи
        cv.put(columnDescription,description); // добавление описания записи

        // добавление новой записи в БД
        long resultValue = db.insert(tableName,null, cv);

        if (resultValue == -1){ // если resultValue возвращает -1, значит добавление записи в БД не удалось
            Toast.makeText(context, "Данные в БД не добавлены", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные в БД успешно добавлены", Toast.LENGTH_SHORT).show();
        }
    }

    // 2) чтение таблицы БД
    public Cursor readTasks(){

        // формирование запроса к БД
        String query = "SELECT * FROM " +  tableName;

        // метод getReadableDatabase() получает БД для чтения
        SQLiteDatabase database= this.getReadableDatabase();

        // создаём нулевой курсор
        Cursor cursor = null;

        if (database != null){ // если БД существует, то инициализируем курсор
            cursor = database.rawQuery(query,null);
        }

        // возврат курсора
        return  cursor;
    }

    // 3) удаление таблицы БД
    public void deleteAllTasks() {

        // проверка подключения к БД
        SQLiteDatabase database = this.getWritableDatabase();

        // формирование запроса удаления таблицы БД
        String query = "DELETE FROM " + tableName;
        database.execSQL(query);

    }

    // 4) обновление записи в БД
    public void updateNotes(String name, String description , String id){

        // проверка подключения к БД
        SQLiteDatabase database =  this.getWritableDatabase();

        // создание контейнера для данных
        ContentValues cv = new ContentValues();
        // запись данных в контейнер
        cv.put(columnName,name);
        cv.put(columnDescription, description);

        // обновление записи по id, где в метод update() подаются
        // (название таблицы, данные для обновления, запись для проверки id, запись в текстовый массив id)
        long result  = database.update(tableName, cv,"id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Обновление не получилось", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Обновление прошло успешно", Toast.LENGTH_SHORT).show();
        }
    }

    // 5) удаление записи по id
    public  void  deleteSingleItem(String id){

        // проверка подключения к БД
        SQLiteDatabase db = this.getWritableDatabase();

        // удаление записи по id, где в метод delete() подаются
        // (название таблицы, запись для проверки id, запись в текстовый массив id)
        long result = db.delete(tableName,"id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Запись не удалена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Запись успешно удалена", Toast.LENGTH_SHORT).show();
        }
    }
}
