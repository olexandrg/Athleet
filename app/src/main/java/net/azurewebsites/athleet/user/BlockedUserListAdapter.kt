package net.azurewebsites.athleet.user

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.ApiLib.Api
import net.azurewebsites.athleet.R
import net.azurewebsites.athleet.dataSource
import net.azurewebsites.athleet.getFirebaseTokenId
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class BlockedUserListAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<String, BlockedUserListAdapter.BlockedUserViewHolder>(BlockedUserDiffCallback) {

    /* ViewHolder for BlockedUser, takes in the inflated view and the onClick behavior. */
    class BlockedUserViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val BlockedUserNameTextView: TextView = itemView.findViewById(R.id.blocked_users_list_username_textView)
        private var BlockedUserUnblockButton = itemView.findViewById<Button>(R.id.blocked_users_list_unblock_button)
        private val api = Api.createSafe();
        fun bind(BlockedUser: String) {
            BlockedUserNameTextView.text = BlockedUser
            BlockedUserUnblockButton.setOnClickListener { api.unblockUser(getFirebaseTokenId(), BlockedUser).enqueue(object:
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        api.retrieveBlockedUsers(getFirebaseTokenId()).enqueue(object:
                            Callback<List<String>> {
                            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                                if(response.isSuccessful) {
                                    dataSource.setBlockList(response.body()!!.toMutableList());
                                }
                            }
                            override fun onFailure(call: Call<List<String>>, t: Throwable) { TODO("Not yet implemented") }
                        })}
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) { TODO("Not yet implemented") } }) }
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