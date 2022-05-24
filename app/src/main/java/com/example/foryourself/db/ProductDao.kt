package com.example.foryourself.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.model.ResultCache

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductsFromService(product: ResultCache)

    @Update
    suspend fun updateDATABASE(product: ResultCache)

    @Query("select * from products_table")
    suspend fun getProductsFromDATABASE(): MutableList<ResultCache>

    @Query("select * from products_table where objectId ==:ID")
    suspend fun getOneProductDetail(ID: String): ResultCache?

    @Query("select * from products_table where tipy == :Tipy")
    suspend fun getTipy(Tipy: String): MutableList<ResultCache>?

    @Query("select * from products_table where category == :category")
    suspend fun getcategory(category: String): MutableList<ResultCache>?

//    @Delete
//    suspend fun deleteDATABASEPROD(product: ResultCache)

    @Query("DELETE FROM products_table WHERE objectId = :id")
    fun deleteDATABASE(id: String)
/////////////////////////////////////////////////////////////////////////////


    @Query("DELETE FROM products_table")
    suspend fun clearTable()

}