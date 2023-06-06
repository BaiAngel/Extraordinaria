package com.example.extraordinaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;

public class RegistrarActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextPhone;
    private RadioGroup radioGroupGender;
    private Spinner spinnerCountry;
    private DatePicker datePickerBirthday;
    private Button buttonSave;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        spinnerCountry = findViewById(R.id.spinnerCountry);

        List<String> countryList = new ArrayList<>();
        countryList.add("España");
        countryList.add("Portugal");
        countryList.add("Inglaterra");
        countryList.add("Rusia");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCountry.setAdapter(adapter);

// Inicializar Firestore
        firestore = FirebaseFirestore.getInstance();

        // Obtener referencias a los elementos del formulario
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        datePickerBirthday = findViewById(R.id.datePickerBirthday);
        buttonSave = findViewById(R.id.buttonSave);

        // Configurar el evento de clic del botón de guardar
        Spinner finalSpinnerCountry = spinnerCountry;
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();
                String phone = editTextPhone.getText().toString();
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
                String gender = selectedGenderRadioButton.getText().toString();
                String country = finalSpinnerCountry.getSelectedItem().toString();
                // Obtener la fecha de nacimiento seleccionada
                int day = datePickerBirthday.getDayOfMonth();
                int month = datePickerBirthday.getMonth() + 1; // Los meses comienzan desde 0
                int year = datePickerBirthday.getYear();

                // Guardar los datos en Firestore
                guardarDatosEnFirestore(name, password, phone, gender, country, day, month, year);
            }
        });
    }

    private void guardarDatosEnFirestore(String name, String password, String phone, String gender, String country, int day, int month, int year) {
        // Aquí puedes definir la estructura de tu colección y documento en Firestore
        // Por ejemplo, podrías crear una colección "usuarios" y utilizar el nombre como ID del documento
        // Asegúrate de adaptar esto a la estructura de tu base de datos en Firestore
        String collection = "usuarios";
        String documentId = name;

        // Crear un objeto con los datos a guardar
        Usuario usuario = new Usuario(name, password, phone, gender, country, day, month, year);

        // Guardar los datos en Firestore
        firestore.collection(collection)
                .document(documentId)
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Éxito al guardar los datos
                        Toast.makeText(RegistrarActivity.this, "Datos guardados correctamente en Firestore", Toast.LENGTH_SHORT).show();
                        // Aquí puedes agregar cualquier acción adicional que desees realizar después de guardar los datos
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al guardar los datos
                        Toast.makeText(RegistrarActivity.this, "Error al guardar los datos en Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}