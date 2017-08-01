package com.ctech.eaty.repository

import com.ctech.eaty.entity.AccessToken
import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.User
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.request.OAuthUserRequest
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.response.UserResponse
import com.ctech.eaty.ui.user.action.UserProductBarCode
import com.nytimes.android.external.store2.base.impl.BarCode
import com.nytimes.android.external.store2.base.impl.Store
import io.reactivex.Observable

class UserRepository(private val apiClient: ProductHuntApi,
                     private val userStore: Store<UserResponse, BarCode>,
                     private val productStore: Store<ProductResponse, UserProductBarCode>,
                     private val appSettings: AppSettingsManager) {

    fun getUserToken(code: String): Observable<AccessToken> = apiClient.getAccessToken(OAuthUserRequest(code = code))

    fun getMe(): Observable<UserDetail> = apiClient.getMe().map {
        it.user
    }

    fun getUser(): Observable<UserDetail> = Observable.just(appSettings.getUser())

    fun getUserById(barcode: BarCode): Observable<UserDetail> = userStore.get(barcode)
            .map {
                it.user
            }
            .retryWhen(RefreshTokenFunc(apiClient, appSettings))

    fun getProduct(barcode: UserProductBarCode): Observable<List<Product>> {
        return productStore.get(barcode)
                .map { it.products }
                .retryWhen(RefreshTokenFunc(apiClient, appSettings))
    }

    fun getFollowers(id: Int, limit: Int, page: Int): Observable<List<User>> {
        return apiClient.getFollowersByUser(id, limit, page)
                .map {
                    it.followers.map { it.user }
                }
    }

    fun getFollowing(id: Int, limit: Int, page: Int): Observable<List<User>> {
        return apiClient.getFollowingByUser(id, limit, page)
                .map {
                    it.followings.map { it.user }
                }
    }

}