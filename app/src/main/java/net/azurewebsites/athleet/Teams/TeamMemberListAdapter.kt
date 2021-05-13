package net.azurewebsites.athleet.Teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.models.TeamUser

class TeamMemberListAdapter(private val onClick: (TeamUser) -> Unit) :
    ListAdapter<TeamUser, TeamMemberListAdapter.TeamMemberViewHolder>(TeamMemberDiffCallback) {

    /* ViewHolder for TeamMember, takes in the inflated view and the onClick behavior. */
    class TeamMemberViewHolder(itemView: View, val onClick: (TeamUser) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val teamMemberNameTextView: TextView = itemView.findViewById(R.id.textView_TeamMemberName)
        private val teamMemberIsAdmin: ImageView = itemView.findViewById(R.id.is_admin_indicator)

        private var currentTeamMember: TeamUser? = null
        init { itemView.setOnClickListener { currentTeamMember?.let { onClick(it) } } }

        /* Bind teamMember name and image. */
        fun bind(teamMember: TeamUser) {
            currentTeamMember = teamMember
            teamMemberNameTextView.text = teamMember.userName
            if(teamMember.isAdmin)
                teamMemberIsAdmin.isVisible = true
            else
                teamMemberIsAdmin.isVisible = false
        }
    }

    /* Creates and inflates view and return TeamMemberViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMemberListAdapter.TeamMemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_member_list_item, parent, false)
        return TeamMemberViewHolder(view, onClick)
    }

    /* Gets current teamMember and uses it to bind view. */
    override fun onBindViewHolder(holder: TeamMemberViewHolder, position: Int) {
        val teamMember = getItem(position)
        holder.bind(teamMember)
    }
}

object TeamMemberDiffCallback : DiffUtil.ItemCallback<TeamUser>() {
    override fun areItemsTheSame(oldItem: TeamUser, newItem: TeamUser): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TeamUser, newItem: TeamUser): Boolean {
        return oldItem == newItem
    }
}
