package com.example.myapplication

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    // ViewHolder trzyma elementy UI (widoki)
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTodoTitle: TextView = itemView.findViewById(R.id.tvTodoTitle)
        val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
    }

    // Tworzymy widok dla nowego elementu
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    // Aktualizujemy widok dla istniejącego elementu
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]

        // Ustawiamy tytuł zadania
        holder.tvTodoTitle.text = todo.tile

        // Ustawiamy stan checkboxa (zaznaczenie/odznaczenie)
        holder.cbDone.isChecked = todo.isChecked

        // Przekreślamy tytuł, jeśli zadanie jest oznaczone jako zrobione
        if (todo.isChecked) {
            holder.tvTodoTitle.paintFlags = holder.tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            holder.tvTodoTitle.paintFlags = holder.tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Nasłuchujemy zmian stanu checkboxa
        holder.cbDone.setOnCheckedChangeListener { _, isChecked ->
            todo.isChecked = isChecked

            // Przekreślamy tytuł, jeśli zadanie jest zaznaczone
            if (isChecked) {
                holder.tvTodoTitle.paintFlags = holder.tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                holder.tvTodoTitle.paintFlags = holder.tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    // Zwracamy liczbę elementów w liście
    override fun getItemCount(): Int = todos.size

    // Dodajemy nowe zadanie do listy
    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    // Usuwamy zakończone zadania z listy
    fun deleteDoneTodos() {
        todos.removeAll { it.isChecked }
        notifyDataSetChanged()
    }
}
