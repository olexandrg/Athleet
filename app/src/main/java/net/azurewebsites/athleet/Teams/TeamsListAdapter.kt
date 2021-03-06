package net.azurewebsites.athleet.Teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.models.Team

class TeamsListAdapter(private val onClick: (Team) -> Unit) :
    ListAdapter<Team, TeamsListAdapter.TeamsViewHolder>(TeamsDiffCallback) {

    // ViewHolder for Workout, takes in the inflated view and the onClick behavior.
    class TeamsViewHolder(itemView: View, val onClick: (Team) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val teamNameTextView: TextView = itemView.findViewById(R.id.textView_TeamName)
        private var currentTeam: Team? = null
        init {
            itemView.setOnClickListener {
                currentTeam?.let {
                    onClick(it)
                }
            }
        }

        /* Bind workout name and image. */
        fun bind(team: Team) {
            currentTeam = team
            teamNameTextView.text = team.teamName
}
    }

    /* Creates and inflates view and return WorkoutViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsListAdapter.TeamsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_list_item, parent, false)
        return TeamsListAdapter.TeamsViewHolder(view, onClick)
    }

    /* Gets current workout and uses it to bind view. */
    override fun onBindViewHolder(holder: TeamsListAdapter.TeamsViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }
}

object TeamsDiffCallback : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.teamName == newItem.teamName
    }
}
