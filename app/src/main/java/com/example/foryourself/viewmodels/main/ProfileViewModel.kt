package com.example.foryourself.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.data.retrofitResponse.postUser.PutUsers
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: OrderRepository,
) : ViewModel() {

    private var _loadingLiveData = MutableLiveData<String>()
    var loadingLiveData: LiveData<String> = _loadingLiveData




    fun newUser(id: String, user: PutUsers) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            var bb : com.example.foryourself.data.retrofitResponse.getUsers.Result? = null

            val otvet = repository.getUser()
            if (otvet.isSuccessful)
                otvet.body()!!.results.forEach { bb = it }

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
            response.body()!!.results.forEach {
                if (it.email == id) {
                    emit(it)
//                    _loadingLiveData.postValue("С возвращением ${it.name}")
                }
            }
        } else Log.d("eret", response.message().toString())
    }


}