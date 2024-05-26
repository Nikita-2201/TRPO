package com.example.lr3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import com.example.lr3.cycle_list.CycleList;
import com.example.lr3.factory.UserFactory;
import com.example.lr3.types.users.UserType;

public class MainActivity extends AppCompatActivity {

    public UserFactory userFactory;
    public UserType userType;
    public CycleList cycleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userFactory = new UserFactory();
        ArrayList<String> typeNameList = new ArrayList<>(userFactory.getTypeNameList());
        String[] types = new String[typeNameList.size()];
        for (int i = 0; i < typeNameList.size(); i++) {
            types[i] = typeNameList.get(i);
        }
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userType = UserFactory.getBuilderByName(parent.getSelectedItem().toString());
                assert userType != null;
                cycleList = new CycleList();
                setTextOnOutTextField();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonInit();
    }

    private void buttonInit() {
        Button deleteByIdButton = (Button) findViewById(R.id.deleteButton);
        Button insertByIdButton = (Button) findViewById(R.id.addButton);
        Button findByIdButton = (Button) findViewById(R.id.findButton);
        Button insertButton = (Button) findViewById(R.id.genButton);

        findByIdButton.setOnClickListener((view) -> {
            EditText findByIdField = (EditText) findViewById(R.id.find);
            if (findByIdField.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "Введите индекс для поиска!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Toast.makeText(getBaseContext(), "Ваш элемент с индексом:\n" + Integer.parseInt(String.valueOf(findByIdField.getText())) + " )" + cycleList.getByIndex(Integer.parseInt(String.valueOf(findByIdField.getText()))).toString(), Toast.LENGTH_LONG).show();
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(getBaseContext(), "Введите правильный индекс для поиска!", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteByIdButton.setOnClickListener((view) -> {
            EditText deleteByIdField = (EditText) findViewById(R.id.delete);
            if (deleteByIdField.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "Введите индекс для удаления!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    cycleList.remove(Integer.parseInt(String.valueOf(deleteByIdField.getText())));
                    setTextOnOutTextField();
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(getBaseContext(), "Введите правильный индекс для удаления!", Toast.LENGTH_LONG).show();
                }
            }
        });

        insertByIdButton.setOnClickListener((view) -> {
            EditText insertByIdField = (EditText) findViewById(R.id.addEditText);
            if (insertByIdField.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "Введите индекс для вставки!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    cycleList.add(userType.create(), Integer.parseInt(String.valueOf(insertByIdField.getText())));
                    setTextOnOutTextField();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Введите правильный индекс для вставки!", Toast.LENGTH_LONG).show();
                }
            }
        });

        insertButton.setOnClickListener((view) -> {
            try {
                cycleList.add(userType.create());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
            setTextOnOutTextField();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sortMenu:
                sortList();
                return true;
            case R.id.saveMenu:
                saveList();
                return true;
            case R.id.loadMenu:
                loadList();
                return true;
            case R.id.clearMenu:
                clearList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearList() {
        cycleList = new CycleList();
        setTextOnOutTextField();
    }

    private void saveList() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter((openFileOutput(userType.typeName() + ".txt", MODE_PRIVATE))));
        } catch (FileNotFoundException e) {
            Toast.makeText(getBaseContext(), "Ошибка при записи файла!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        try {
            bufferedWriter.write(userType.typeName() + "\n");
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Ошибка при записи файла!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        BufferedWriter finalBufferedWriter = bufferedWriter;
        try {
            cycleList.forEach(el -> {
                try {
                    finalBufferedWriter.write(el.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Toast.makeText(getBaseContext(), "Список успешно сохранен в файл!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Ошибка при записи файла!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadList() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader((openFileInput(userType.typeName() + ".txt"))));
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Ошибка при чтении файла!", Toast.LENGTH_LONG).show();
            return;
        }
        String line;
        try {
            line = bufferedReader.readLine();
            if (line == null) {
                Toast.makeText(getBaseContext(), "Ошибка при чтении файла!", Toast.LENGTH_LONG).show();
                return;
            }
            if (!userType.typeName().equals(line)) {
                Toast.makeText(getBaseContext(), "Неправильный формат файла!", Toast.LENGTH_LONG).show();
                return;
            }
            cycleList = new CycleList();

            while ((line = bufferedReader.readLine()) != null) {
                try {
                    cycleList.add(userType.parseValue(line));
                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), "Ошибка при чтении файла!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTextOnOutTextField();
    }

    private void sortList() {
        cycleList.sort(userType.getTypeComparator());
        setTextOnOutTextField();
    }

    private void setTextOnOutTextField() {
        ListView mainList = (ListView) findViewById(R.id.mainList);
        String[] elems = cycleList.toString().split("\n");
        ArrayList<String> listStr = new ArrayList<>();
        Collections.addAll(listStr, elems);
        ArrayAdapter<String> adapterList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listStr);
        mainList.setAdapter(adapterList);
    }

}