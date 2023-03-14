package com.example.rickyandmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmorty.model.CharacterModel
import com.example.rickyandmorty.retrofit.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    val characterDetails=MutableLiveData<CharacterModel>()
    val disposable =CompositeDisposable()
    val error=MutableLiveData<Boolean>()

    fun getCharacterDetails(id:Int){
        viewModelScope.launch{
            disposable.add(RetrofitService.api.getCharacter(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<CharacterModel>(){
                    override fun onSuccess(t: CharacterModel) {
                        characterDetails.value=t
                        error.value=false
                    }

                    override fun onError(e: Throwable) {
                        error.value=true
                    }

                }))
        }
    }
}