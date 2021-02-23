package net.azurewebsites.athleet

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.exercise.ExerciseListAdapter
import net.azurewebsites.athleet.models.Exercise

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
    data: List<Exercise>?) {
        val adapter = recyclerView.adapter as ExerciseListAdapter
        adapter.submitList(data)
}