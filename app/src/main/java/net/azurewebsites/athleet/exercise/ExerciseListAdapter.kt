package net.azurewebsites.athleet.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.azurewebsites.athleet.databinding.ExerciseListItemBinding
import net.azurewebsites.athleet.models.Exercise

class ExerciseListAdapter(private val onClickListener: OnClickListener) : ListAdapter<Exercise, ExerciseListAdapter.ExerciseViewHolder>(DiffCallback){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseListAdapter.ExerciseViewHolder {
        return ExerciseViewHolder(ExerciseListItemBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: ExerciseListAdapter.ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(exercise)
        }
        holder.bind(exercise)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Exercise>() {

        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.exerciseId == newItem.exerciseId
        }
    }

    class ExerciseViewHolder(private var binding:
                ExerciseListItemBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(exercise: Exercise) {
            binding.exercise = exercise
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (exercise: Exercise) -> Unit) {
        fun onClick(exercise: Exercise) = clickListener(exercise)
    }

}
