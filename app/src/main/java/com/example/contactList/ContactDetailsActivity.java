package com.example.contactList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactDetailsActivity extends AppCompatActivity {
    EditText name;
    EditText lastName;
    EditText phoneNumber;
    EditText address;
    Button update;
    Button delete; int id;
    Person person;
    final DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        id = getIntent().getExtras().getInt("id");
        person = databaseHelper.viewContact(id);

        name= findViewById(R.id.txt_nameD);
        lastName = findViewById(R.id.txt_last_nameD);
        phoneNumber = findViewById(R.id.txt_phone_numberD);
        address=findViewById(R.id.txt_addressD);

        update = findViewById(R.id.btn_update);
        delete = findViewById(R.id.btn_delete);


        name.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        phoneNumber.setText(person.getPhoneNumber());
        address.setText(person.getAddress());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().equals("") && !lastName.getText().toString().equals("") && !phoneNumber.getText().toString().equals("") && !address.getText().toString().equals("")){


                    person.setFirstName(name.getText().toString());
                    person.setLastName(lastName.getText().toString());
                    person.setPhoneNumber(phoneNumber.getText().toString());
                    person.setAddress(address.getText().toString());

                    databaseHelper.updatePerson(person);
                    Toast.makeText(ContactDetailsActivity.this,"Contact updated successfully",Toast.LENGTH_SHORT).show();
                    finish();

                }

                else {
                    Toast.makeText(ContactDetailsActivity.this,"Please fill all contact details",Toast.LENGTH_SHORT).show();
                }
            }
        });

//delete button action
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetailsActivity.this);
                builder.setTitle("Are you sure to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHelper.deleteContact(person.getId());
                        Toast.makeText(ContactDetailsActivity.this," Contact deleted successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }

        });

    }
}