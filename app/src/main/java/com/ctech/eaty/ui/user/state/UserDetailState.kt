package com.ctech.eaty.ui.user.state

import com.ctech.eaty.entity.UserDetail

data class UserDetailState(val loading: Boolean = false,
                           val loadError: Throwable? = null,
                           val content: UserDetail? = UserDetail.GUEST)