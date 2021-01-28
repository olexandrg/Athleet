package net.azurewebsites.athleet.Exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.TeamItem
import net.azurewebsites.athleet.Exercises.Exercise

class ExerciseListAdapter(private val onClick: (Exercise) -> Unit) :
    ListAdapter<Exercise, ExerciseListAdapter.ExerciseViewHolder>(ExerciseDiffCallback) {

    /* ViewHolder for Exercise, takes in the inflated view and the onClick behavior. */
    class ExerciseViewHolder(itemView: View, val onClick: (Exercise) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val exerciseNameTextView: TextView = itemView.findViewById(R.id.textView_ExerciseName)
        private val exerciseSetsTextView: TextView = itemView.findViewById(R.id.textView_ExerciseSets)
        private val exerciseRepsTextView: TextView = itemView.findViewById(R.id.textView_ExerciseReps)

        //private val exerciseDateTextView: TextView = itemView.findViewById(R.id.add_exercise_description)
        private var currentExercise: Exercise? = null

        init {
            itemView.setOnClickListener {
                currentExercise?.let {
                    onClick(it)
                }
            }
        }

        /* Bind exercise name and image. */
        fun bind(exercise: Exercise) {
            currentExercise = exercise

            exerciseNameTextView.text = exercise.name.toString()
            exerciseRepsTextView.text = "Reps: " + exercise.reps.toString()
            exerciseSetsTextView.text="Sets: "+exercise.sets.toString()
            //exerciseDateTextView.text = exercise.lastCompleted.toString()
        }
    }

    /* Creates and inflates view and return ExerciseViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseViewHolder(view, onClick)
    }

    /* Gets current exercise and uses it to bind view. */
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }
}

object ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.name == newItem.name
    }
}
