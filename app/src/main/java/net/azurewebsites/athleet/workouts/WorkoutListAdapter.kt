package net.azurewebsites.athleet.workouts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.models.Workout

class WorkoutListAdapter(private val onClick: (Workout) -> Unit) :
    ListAdapter<Workout, WorkoutListAdapter.WorkoutViewHolder>(WorkoutDiffCallback) {

    /* ViewHolder for Workout, takes in the inflated view and the onClick behavior. */
    class WorkoutViewHolder(itemView: View, val onClick: (Workout) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val workoutTextView: TextView = itemView.findViewById(R.id.textView_WorkoutName)
        private val workoutDateTextView: TextView = itemView.findViewById(R.id.textView_WorkoutDescription)
        private var currentWorkout: Workout? = null

        init { itemView.setOnClickListener { currentWorkout?.let { onClick(it) } } }

        /* Bind workout name and image. */
        fun bind(workout: Workout) { currentWorkout = workout
            workoutTextView.text = workout.workoutName
        }
    }

    /* Creates and inflates view and return WorkoutViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_list_item, parent, false)
        return WorkoutViewHolder(view, onClick)
    }

    /* Gets current workout and uses it to bind view. */
    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }
}

object WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.workoutName == newItem.workoutName
    }
}
