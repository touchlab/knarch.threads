package co.touchlab.multiplatform.architecture.livedata

//Can't expect/actual LiveData because of this issue https://youtrack.jetbrains.com/issue/KT-19848

expect open class MutableLiveData<T>(){
    fun getValue():T?
    open fun setValue(value:T)
    open fun postValue(value:T)
}

expect interface Observer<T>{
    fun onChanged(t: T?)
}

expect class MediatorLiveData<T>():MutableLiveData<T> {
}

expect fun <S, T> MediatorLiveData<T>.addSource(source:MutableLiveData<S>, onChanged:Observer<S>)
expect fun <S, T> MediatorLiveData<T>.removeSource(toRemote:MutableLiveData<S>)

fun <S, T> MediatorLiveData<T>.addSource(source: MutableLiveData<S>, onChangedProc: (S)->Unit){
    this.addSource(source, object : Observer<S> {
        override fun onChanged(t: S?) {
            if(t != null)
                onChangedProc(t)
        }
    })
}