package net.azurewebsites.athleet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//      Data Classes must be modeled after JSON response
//      JSON response from api/Users/
//      [{"userId":1,"userName":"SimiF","userHeadline":"Hi","firebaseUID":"FakeFirebaseUID"}]

//      Since response is wrapped in [], need an aggregate class of Users, which contains single
//      records of individual User classes

class Users {
    @SerializedName("userID")
    @Expose
    val items: List<User>?= null
}

class User {
    @SerializedName("userName")
    @Expose
    val userName:String? = null

    @SerializedName("userHeadline")
    @Expose
    val userHeadline:String? = null

    @SerializedName("firebaseUID")
    @Expose
    val firebaseUID:String? = null
}