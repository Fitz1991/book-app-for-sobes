package com.infoshell.data.repository.user

import com.infoshell.data.entity.ApiUser

interface UserStorageSource {

    fun getUser(): ApiUser?

    fun saveUser(user: ApiUser)

    fun checkUser(): Boolean
}