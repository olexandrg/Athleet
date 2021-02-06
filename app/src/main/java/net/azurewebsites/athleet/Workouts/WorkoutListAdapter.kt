package net.azurewebsites.athleet.Workouts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.Teams.TeamItem

class WorkoutListAdapter(private val onClick: (Workout) -> Unit) :
    ListAdapter<Workout, WorkoutListAdapter.WorkoutViewHolder>(WorkoutDiffCallback) {

    /* ViewHolder for Workout, takes in the inflated view and the onClick behavior. */
    class WorkoutViewHolder(itemView: View, val onClick: (Workout) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val workoutTextView: TextView = itemView.findViewById(R.id.textView_WorkoutName)
        private val workoutDateTextView: TextView = itemView.findViewById(R.id.textView_LastWorkoutDate)
        private var currentWorkout: Workout? = null

        init {
            itemView.setOnClickListener {
                currentWorkout?.let {
                    onClick(it)
                }
            }
        }

        /* Bind workout name and image. */
        fun bind(workout: Workout) { currentWorkout = workout
            workoutTextView.text = workout.name
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
        return oldItem.name == newItem.name
    }
}
class TeamsListAdapter(private val onClick: (TeamItem) -> Unit) :
    ListAdapter<TeamItem, TeamsListAdapter.TeamsViewHolder>(TeamsDiffCallback) {

    /* ViewHolder for Workout, takes in the inflated view and the onClick behavior. */
    class TeamsViewHolder(itemView: View, val onClick: (TeamItem) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private var currentTeam: TeamItem? = null
        init {
            itemView.setOnClickListener {
                currentTeam?.let {
                    onClick(it)
                }
            }
        }

        /* Bind workout name and image. */
        fun bind(team: TeamItem) {
            currentTeam = team
}
    }

    /* Creates and inflates view and return WorkoutViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsListAdapter.TeamsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_list_item, parent, false)
        return TeamsListAdapter.TeamsViewHolder(view, onClick)
    }

    /* Gets current workout and uses it to bind view. */
    override fun onBindViewHolder(holder: TeamsListAdapter.TeamsViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }
}

object TeamsDiffCallback : DiffUtil.ItemCallback<TeamItem>() {
    override fun areItemsTheSame(oldItem: TeamItem, newItem: TeamItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TeamItem, newItem: TeamItem): Boolean {
        return oldItem.teamName == newItem.teamName
    }
}
