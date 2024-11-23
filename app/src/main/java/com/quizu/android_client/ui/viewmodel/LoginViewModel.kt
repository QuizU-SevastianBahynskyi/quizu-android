package com.quizu.android_client.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.quizu.android_client.SecureStorage
import com.quizu.android_client.data.ApiResult
import com.quizu.android_client.data.safeApiCall
import com.quizu.java.JSON
import com.quizu.kotlin.api.UserControllerApi
import com.quizu.kotlin.model.LoginRequest
import com.quizu.kotlin.model.TokenResponse
import com.quizu.kotlin.model.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.openapitools.client.infrastructure.ApiResponse
import javax.inject.Inject
import kotlin.math.log

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val loginResult: ApiResult<UserAccount>? = null  // Use generic Result here
)

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val secureStorage: SecureStorage
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    open val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()
    private val httpClient: UserControllerApi = UserControllerApi(basePath = "http://localhost:8080")

    fun login() {
        viewModelScope.launch {
            val logTag: String = "ApiCall.login";
            _uiState.value = _uiState.value.copy(loginResult = ApiResult.Loading)

            val loginRequest = LoginRequest(
                email = uiState.value.email,
                password = uiState.value.password
            )

            val result = safeApiCall {
                httpClient.userLoginPost(loginRequest)
            }



            when(result) {
                is ApiResult.Success -> {
                    val token = result.data.token!!
                    secureStorage.saveJwtToken(token)
                    val jwt = JWT(token)
                    val username = jwt.getClaim("username").asString()
                    val email = jwt.subject.toString()
                    val userAccount = UserAccount(email = email, username = username)
                    Log.d("${logTag}.Success", "$userAccount")
                }
                is ApiResult.Error -> {
                    Log.e(logTag, "Error: ${result.message}")
                    _uiState.value = _uiState.value.copy(loginResult = result)
                }

                is ApiResult.Loading -> TODO("Maybe show a loading spinner?")
            }
        }
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }
}
