package com.example.recycleview.model

import com.github.javafaker.Faker
import java.util.*
import kotlin.collections.ArrayList

typealias UserListener = (users:List<User>) -> Unit

class UsersService {

    private var users: MutableList<User> = mutableListOf<User>()

    private val listeners : MutableSet<UserListener> = mutableSetOf<UserListener>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..100).map { User(
            id = it.toLong(),
            name = faker.name().name(),
            company = faker.company().name(),
            photo = IMAGES[it % IMAGES.size]
        )}.toMutableList()
    }

    fun getUsers() : List<User>{
        return users
    }

    fun deleteUser(user: User){
        val indexToDelete:Int = users.indexOfFirst{ it.id == user.id }
        if (indexToDelete !=-1){
            users = ArrayList(users)
            users.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun moveUser(user: User, moveBy:Int){
        val oldIndex:Int = users.indexOfFirst{ it.id == user.id }
        if(oldIndex == -1) return
        val newIndex : Int = oldIndex + moveBy
        if(newIndex < 0 || newIndex >= users.size)return
        users = ArrayList(users)
        Collections.swap(
            users,
            oldIndex,
            newIndex
        )
        notifyChanges()
    }

    fun fireUser(user: User){
        val index:Int = users.indexOfFirst{ it.id == user.id }
        if (index == -1) return
        val updatedUser = users[index].copy(company = "")
        users = ArrayList(users)
        users[index] = updatedUser
        notifyChanges()
    }

    fun addListener(listener:UserListener){
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UserListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(users)}
    }

    companion object{
        private val IMAGES = mutableListOf(
            "https://images.generated.photos/tTABl8n4owJwiy7dmDBoQqL_AG8ZwS4QelsjBqLitek/rs:fit:512:512/wm:0.95:sowe:18:18:0.33/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LmNvbmQvYzI5NjQw/NjktYzRhNC00ZTY2/LWIyMWYtNWVhN2M0/MzA2YmJlLmpwZw.jpg",
            "https://images.generated.photos/siwlki0PRb511c3r0-kjkjyTxfNbPjsXKO-iPPsBhac/rs:fit:512:512/wm:0.95:sowe:18:18:0.33/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LnBob3Rvcy92M18w/NjUwMDcyLmpwZw.jpg",
            "https://images.generated.photos/pn-4ub1PeBlBuDILgChTqIwjBIMhxm-Ah_rzk9t0GD8/rs:fit:256:256/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LnBob3Rvcy92M18w/ODUwMDk3LmpwZw.jpg",
            "https://images.generated.photos/odi65_e9sIxrxDz0_q5j7_lxfHmnvjU_mb2MOCp8GHI/rs:fit:512:512/wm:0.95:sowe:18:18:0.33/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LnBob3Rvcy92M18w/NjI5OTg0LmpwZw.jpg",
            "https://images.generated.photos/tTABl8n4owJwiy7dmDBoQqL_AG8ZwS4QelsjBqLitek/rs:fit:512:512/wm:0.95:sowe:18:18:0.33/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LmNvbmQvYzI5NjQw/NjktYzRhNC00ZTY2/LWIyMWYtNWVhN2M0/MzA2YmJlLmpwZw.jpg",
            "https://images.generated.photos/siwlki0PRb511c3r0-kjkjyTxfNbPjsXKO-iPPsBhac/rs:fit:512:512/wm:0.95:sowe:18:18:0.33/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LnBob3Rvcy92M18w/NjUwMDcyLmpwZw.jpg",
            "https://images.generated.photos/pn-4ub1PeBlBuDILgChTqIwjBIMhxm-Ah_rzk9t0GD8/rs:fit:256:256/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LnBob3Rvcy92M18w/ODUwMDk3LmpwZw.jpg",
            "https://images.generated.photos/odi65_e9sIxrxDz0_q5j7_lxfHmnvjU_mb2MOCp8GHI/rs:fit:512:512/wm:0.95:sowe:18:18:0.33/czM6Ly9pY29uczgu/Z3Bob3Rvcy1wcm9k/LnBob3Rvcy92M18w/NjI5OTg0LmpwZw.jpg",
        )
    }
}