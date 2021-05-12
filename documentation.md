# Code documentation

This file is to provide documentation to different aspects of our code.

## Writing in this file

* To write a code snippet it can look like below.

```
some code
```

or

```kotlin
this is a kotlin code snippet
```


# Activity v. Fragment
An Activity is a single screen within an app. Where as a Fragment is a modular piece of an activity that can be used by multiple activities. As well as an activity may have many fragments.

## When to use Fragments
A fragment is useful when you want to be able to reuse UI components. An example could be two lists that format things the same but use different data. That could be an ideal use for a fragment.

A disadvantage is the potential for added complexity.

An advantage is if you want to use some layout amongst multiple activities.

A fragment must be attached to an activity to be used.

## When to use Activities
Pretty much all other instances not covered above.


# Calling the API Provider
Things surrounded by `<>` are to be replaced with the correct variable or type.

```kotlin
//initiate the api
val api = Api.createSafe()

//get the api call
val call = api.<method name>(<token>, <other args>...)

//make an asyncronous call to the api on another thread
    //and provide a callback class with callbacks for success and failure
call.enqueue(object: Callback<<return type>> {
    override fun onResponse(call: Call<<return type>>, response: Response<<return type>>) {
        //call a toast to tell the user it was successful
    }
    override fun onFailure(call: Call<<return type>>, t: Throwable) {
        //call a toast to tell the user something went wrong
    }
})
```
