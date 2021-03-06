package com.example.foryourself.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.users.postUser.PutUsers
import com.example.foryourself.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: OrderRepository,
) : ViewModel() {

    private var _loadingLiveData = MutableLiveData<String>()
    var loadingLiveData: LiveData<String> = _loadingLiveData




    fun newUser(id: String, user: PutUsers) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            var bb : com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata? = null

            val otvet = repository.getUser()
            if (otvet.isSuccessful)
                otvet.body()!!.rrresults.forEach { bb = it }

            if (user.email != bb!!.email) {
                if (repository.postUser(user).isSuccessful) {
                    emit("Ваши данные успешно были добавлены")
                }
            } else if (user.email == bb!!.email && user.numberTelephone != bb!!.numberTelephone) {
                if (repository.updateUser(id, user).isSuccessful) {
                    emit("Ваши данные успешно были обновлены")
                }
            } else {
                emit("У вас актуальные данные")
            }
        }

    fun getUser(id: String) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

        val response = repository.getUser()
        if (response.isSuccessful) {
            response.body()!!.rrresults.forEach {
                if (it.email == id) {
                    emit(it)
//                    _loadingLiveData.postValue("С возвращением ${it.name}")
                }
            }
        } else Log.d("eret", response.message().toString())
    }


}