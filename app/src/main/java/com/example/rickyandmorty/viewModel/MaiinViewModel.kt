package com.example.rickyandmorty.viewModel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmorty.model.CharacterModel
import com.example.rickyandmorty.model.LocationModelX
import com.example.rickyandmorty.model.Result
import com.example.rickyandmorty.retrofit.RetrofitService.api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MaiinViewModel(val view:View) : ViewModel() {

    val locationResult=MutableLiveData<List<Result>>()
    val characterResult=MutableLiveData<List<CharacterModel>>()
    val emptyItems=MutableLiveData<Boolean>()
    val disposable =CompositeDisposable()

    fun getLocationData(){
        disposable.add(api.getLocation().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<LocationModelX>(){
                override fun onSuccess(t: LocationModelX) {
                    locationResult.value=t.results
                }
                override fun onError(e: Throwable) {
                    Toast.makeText(view.context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }))
    }

    fun getCharacterData(characterItems:List<Int>){
        viewModelScope.launch {
            if (characterItems.isEmpty()){
               emptyItems.value=true
            }else{
                val characterList=ArrayList<CharacterModel>()
                for (character in characterItems){
                    disposable.add(api.getCharacter(character).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object :DisposableSingleObserver<CharacterModel>(){
                            override fun onSuccess(t: CharacterModel) {
                                emptyItems.value=false
                                characterList.add(t)
                                characterResult.value=characterList
                            }
                            override fun onError(e: Throwable) {
                                Toast.makeText(view.context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                            }
                        }))
                }
            }
        }
    }
}