package net.azurewebsites.athleet.Teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.models.Conversation
import net.azurewebsites.athleet.models.Team
import net.azurewebsites.athleet.models.UserConvs

class ChatListAdapter(private val onClick: (UserConvs) -> Unit) :
    ListAdapter<UserConvs, ChatListAdapter.ChatViewHolder>(ChatDiffCallback) {

    // ViewHolder for Workout, takes in the inflated view and the onClick behavior.
    class ChatViewHolder(itemView: View, val onClick: (UserConvs) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val teamNameTextView: TextView = itemView.findViewById(R.id.textView_UserName)
        private var currentTeam: UserConvs? = null
        init {
            itemView.setOnClickListener {
                currentTeam?.let {
                    onClick(it)
                }
            }
        }

        /* Bind workout name and image. */
        fun bind(team: UserConvs) {
            currentTeam = team
            teamNameTextView.text = team.userName
        }
    }

    /* Creates and inflates view and return WorkoutViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListAdapter.ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_list_item, parent, false)
        return ChatListAdapter.ChatViewHolder(view, onClick)
    }

    /* Gets current workout and uses it to bind view. */
    override fun onBindViewHolder(holder: ChatListAdapter.ChatViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }
}

object ChatDiffCallback : DiffUtil.ItemCallback<UserConvs>() {
    override fun areItemsTheSame(oldItem: UserConvs, newItem: UserConvs): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserConvs, newItem: UserConvs): Boolean {
        return oldItem.userName == newItem.userName
    }
}