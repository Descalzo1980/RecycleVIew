package com.example.recycleview

import android.app.Application
import com.example.recycleview.model.UsersService

class App : Application(){

    val usersService = UsersService()
}