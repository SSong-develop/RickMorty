package com.ssong_develop.rickmorty.repository

interface Repository {

    // this override property is for saving network loading state

    /**
     * SSong-develop
     *
     * 이걸 ViewModel에서 관리하는 건 이상한가 어차피 뷰의 데이터인데
     */
    var isLoading: Boolean
}