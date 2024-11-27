package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val students = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewStudents)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
        listView.adapter = adapter

        // Đăng ký Context Menu
        registerForContextMenu(listView)

        // Option Menu (Add new)
        listView.setOnCreateContextMenuListener { _, _, _ -> }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                // Chuyển đến AddEditStudentActivity để thêm sinh viên
                val intent = Intent(this, AddEditStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_ADD)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.menu_edit -> {
                // Chuyển đến AddEditStudentActivity để chỉnh sửa sinh viên
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("name", students[info.position])
                intent.putExtra("position", info.position)
                startActivityForResult(intent, REQUEST_CODE_EDIT)
                true
            }
            R.id.menu_remove -> {
                // Xóa sinh viên
                students.removeAt(info.position)
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("name") ?: ""
            val position = data.getIntExtra("position", -1)
            when (requestCode) {
                REQUEST_CODE_ADD -> {
                    // Thêm mới sinh viên
                    students.add(name)
                    adapter.notifyDataSetChanged()
                }
                REQUEST_CODE_EDIT -> {
                    // Cập nhật thông tin sinh viên
                    if (position >= 0) {
                        students[position] = name
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_ADD = 1
        const val REQUEST_CODE_EDIT = 2
    }
}