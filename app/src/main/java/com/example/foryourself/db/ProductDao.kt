package com.example.foryourself.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foryourself.db.model.ResultCache

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductsFromRepository(product: ResultCache)

    @Update
    suspend fun updateProductsFromRepository(product: ResultCache)

    @Query("select * from products_table")
    suspend fun getProductsFromDATABASE(): MutableList<ResultCache>

    @Query("select * from products_table where objectId ==:ID")
    suspend fun getOneProductDetail(ID: String): ResultCache

    @Delete
    suspend fun deleteDATABASE(product: ResultCache)

/////////////////////////////////////////////////////////////////////////////


    @Query("DELETE FROM products_table")
    suspend fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorProducts(product: ResultCache)

    @Delete
    suspend fun deleteFromFav(product: ResultCache)

    @Query("select * from products_table")
    fun getFromFav(): LiveData<ResultCache>?

    @Query("select * from products_table where objectId ==:ID")
    suspend fun getOnegetFromFav(ID: String): ResultCache
}