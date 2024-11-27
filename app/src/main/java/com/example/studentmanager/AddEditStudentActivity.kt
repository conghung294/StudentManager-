package com.example.studentmanager

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextStudentId: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        editTextName = findViewById(R.id.editTextName)
        editTextStudentId = findViewById(R.id.editTextStudentId)
        buttonSave = findViewById(R.id.buttonSave)

        // Lấy dữ liệu truyền vào nếu chỉnh sửa
        val name = intent.getStringExtra("name")
        val position = intent.getIntExtra("position", -1)

        if (name != null) {
            editTextName.setText(name)
        }

        // Lưu dữ liệu khi nhấn nút "Lưu"
        buttonSave.setOnClickListener {
            val resultIntent = intent
            resultIntent.putExtra("name", editTextName.text.toString())
            resultIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
