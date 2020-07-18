package com.example.contactList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
    EditText address;
    Button button;

    final DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        firstName = findViewById(R.id.edt_first_name);
        lastName = findViewById(R.id.edt_last_name);
        phoneNumber = findViewById(R.id.edt_phone_number);
        address=findViewById(R.id.edt_address);
        button = findViewById(R.id.btn_add_person);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") && !phoneNumber.getText().toString().equals("") && !address.getText().toString().equals("")){

                    Person person = new Person();
                    person.setFirstName(firstName.getText().toString());
                    person.setLastName(lastName.getText().toString());
                    person.setPhoneNumber(phoneNumber.getText().toString());
                    person.setAddress(address.getText().toString());

                    databaseHelper.addPerson(person);
                    Toast.makeText(AddContactActivity.this,"Contact has been added successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }

                else {
                    Toast.makeText(AddContactActivity.this,"Please fill all the details",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}