//package net.azurewebsites.athleet.Dashboard
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import net.azurewebsites.athleet.R
//
//// A list always displaying one element: the number of workouts.
//
//class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {
//    private var workoutCount: Int = 0
//
//    /* ViewHolder for displaying header. */
//    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
//        private val workoutNumberTextView: TextView = itemView.findViewById(R.id.workout_number_text)
//
//        fun bind(workoutCount: Int) {
//            workoutNumberTextView.text = workoutCount.toString()
//        }
//    }
//
//    /* Inflates view and returns HeaderViewHolder. */
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.header_item, parent, false)
//        return HeaderViewHolder(view)
//    }
//
//    /* Binds number of workouts to the header. */
//    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
//        holder.bind(workoutCount)
//    }
//
//    /* Returns number of items, since there is only one item in the header return one  */
//    override fun getItemCount(): Int {
//        return workoutCount
//    }
//
//    /* Updates header to display number of workouts when a workout is added or subtracted. */
//    fun updateWorkoutCount(updatedWorkoutCount: Int) {
//        workoutCount = updatedWorkoutCount
//        notifyDataSetChanged()
//    }
//}