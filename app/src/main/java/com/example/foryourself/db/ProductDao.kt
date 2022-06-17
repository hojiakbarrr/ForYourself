package com.example.foryourself.db

import androidx.room.*
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.ResultofReklama
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.ResultUsersOrder
import com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata
import com.example.foryourself.db.model.FavoritesCache
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
/////////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReklama(reklama: ResultofReklama)


    @Query("select * from resultofreklama")
    suspend fun getreklamaFromDATABASE(): MutableList<ResultofReklama>

    @Query("DELETE FROM resultofreklama")
    suspend fun clearTableReklama()
/////////////////////////////////////////////////////////////////////////////


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorites(product: FavoritesCache)

    @Query("select * from favoritescache")
    suspend fun getFavorites(): MutableList<FavoritesCache>

    @Update
    suspend fun updateFav(product: FavoritesCache)

    @Delete
    fun deleteFrom(product: FavoritesCache)

    @Query("DELETE FROM favoritescache")
    suspend fun clearTableFav()

//////////////////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(user: ResultUserdata)

    @Query("select * from resultuserdata")
    suspend fun getUsers(): MutableList<ResultUserdata>

    @Query("select * from resultuserdata where email == :email")
    suspend fun getMyUserEmail(email: String): MutableList<ResultUserdata>?

    @Query("select * from resultuserdata where name == :name")
    suspend fun getMyUserName(name: String): MutableList<ResultUserdata>?

    @Query("select * from resultuserdata where objectId == :id")
    suspend fun getMyUserId(id: String): MutableList<ResultUserdata>?

    @Update
    suspend fun updatUser(user: ResultUserdata)

    @Query("DELETE FROM resultuserdata")
    suspend fun clearTableUser()

/////////////////////////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsersOrder(userOrders: ResultUsersOrder)

    @Query("select * from resultusersorder where title == :title")
    suspend fun getUsersOrderTITLE(title: String): MutableList<ResultUserdata>?

    @Query("select * from resultusersorder")
    suspend fun getUsersOrder(): MutableList<ResultUsersOrder>

    @Update
    suspend fun updateUserOrder(userOrders: ResultUsersOrder)

    @Query("DELETE FROM resultusersorder")
    suspend fun clearTableUserOrder()

    @Delete
    fun deleteUserOrder(userOrders: ResultUsersOrder)


}