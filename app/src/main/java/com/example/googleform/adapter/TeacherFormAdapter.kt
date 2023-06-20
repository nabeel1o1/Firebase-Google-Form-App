package com.example.googleform.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.googleform.R
import com.example.googleform.databinding.LayoutTeacherFormBinding
import com.example.googleform.datamodel.Teacher

class TeacherFormAdapter(
    private val mContext: Context,
    private val onEditTeacher : (teacher: Teacher) -> Unit,
    private val onDeleteTeacher : (teacherId: String) -> Unit
) : Adapter<TeacherFormAdapter.TeacherFormViewHolder>() {

    private var teacherList = mutableListOf<Teacher>()

    @SuppressLint("NotifyDataSetChanged")
    fun populateFormData(teacherList: List<Teacher>) {
        this.teacherList = teacherList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherFormViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val binding = LayoutTeacherFormBinding.inflate(inflater, parent, false)
        return TeacherFormViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherFormViewHolder, position: Int) {

        val teacherData = teacherList[position]

        with(LayoutTeacherFormBinding.bind(holder.itemView)) {
            tvTeacherName.text = mContext.getString(R.string.teacher_name, teacherData.name)
            tvEmail.text = mContext.getString(R.string.teacher_email, teacherData.email)
            tvCourse.text = mContext.getString(R.string.course, teacherData.course)

            btnEdit.setOnClickListener {
                onEditTeacher(teacherData)
            }

            btnDelete.setOnClickListener {
                onDeleteTeacher(teacherData.id)
            }
        }
    }

    override fun getItemCount(): Int = teacherList.size

    inner class TeacherFormViewHolder(teacherFormBinding: LayoutTeacherFormBinding)
        : ViewHolder(teacherFormBinding.root)
}