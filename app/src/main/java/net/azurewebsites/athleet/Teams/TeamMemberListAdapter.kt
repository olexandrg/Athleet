package net.azurewebsites.athleet.Teams

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import net.azurewebsites.athleet.Dashboard.TeamMemberModel
import net.azurewebsites.athleet.R

class TeamMemberListAdapter(private val activity: Activity, teamList: List<TeamMemberModel>) : BaseAdapter() {
    private var teamList = ArrayList<TeamMemberModel>()

    init {
        this.teamList = teamList as ArrayList
    }

    override fun getCount(): Int {
        return teamList.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View = convertView as View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.activity_team_member_list_view, null)
        val name : TextView = vi.findViewById(R.id.name)
        name.text = teamList[i].name
        return vi
    }
}