package net.azurewebsites.athleet.user

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.dataSource
@RequiresApi(Build.VERSION_CODES.O)
class BlockedUserListAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<String, BlockedUserListAdapter.BlockedUserViewHolder>(BlockedUserDiffCallback) {

    /* ViewHolder for BlockedUser, takes in the inflated view and the onClick behavior. */
    class BlockedUserViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val BlockedUserNameTextView: TextView = itemView.findViewById(R.id.blocked_users_list_username_textView)
        private var BlockedUserUnblockButton = itemView.findViewById<Button>(R.id.blocked_users_list_unblock_button)
        fun bind(BlockedUser: String) {
            BlockedUserNameTextView.text = BlockedUser
            BlockedUserUnblockButton.setOnClickListener { dataSource.unblockUser(BlockedUser); }
        }
    }

    /* Creates and inflates view and return BlockedUserViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockedUserListAdapter.BlockedUserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.blocked_user_list_item, parent, false)
        return BlockedUserViewHolder(view)
    }

    /* Gets current BlockedUser and uses it to bind view. */
    override fun onBindViewHolder(holder: BlockedUserViewHolder, position: Int) {
        val BlockedUser = getItem(position)
        holder.bind(BlockedUser)
    }
}

object BlockedUserDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}