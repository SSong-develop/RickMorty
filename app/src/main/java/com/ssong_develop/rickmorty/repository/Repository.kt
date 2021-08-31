package com.ssong_develop.rickmorty.repository

interface Repository {

    // this override property is for saving network loading state
    var isLoading : Boolean
}