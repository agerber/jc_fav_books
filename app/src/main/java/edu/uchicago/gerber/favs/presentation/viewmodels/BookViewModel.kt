package edu.uchicago.gerber.favs.presentation.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import edu.uchicago.gerber.favs.authorization.AmplifyService
import edu.uchicago.gerber.favs.authorization.AmplifyServiceImpl
import edu.uchicago.gerber.favs.authorization.LoginState
import edu.uchicago.gerber.favs.authorization.SignUpState
import edu.uchicago.gerber.favs.authorization.VerificationCodeState
import edu.uchicago.gerber.favs.common.Constants
import edu.uchicago.gerber.favs.data.models.Item
import edu.uchicago.gerber.favs.data.repository.ApiProvider
import edu.uchicago.gerber.favs.data.repository.BooksRepository
import edu.uchicago.gerber.favs.presentation.screens.search.paging.BookSource
import edu.uchicago.gerber.favs.presentation.screens.search.paging.Paginate
import edu.uchicago.gerber.favs.presentation.screens.search.paging.SearchOperation
import edu.uchicago.gerber.favs.presentation.screens.search.paging.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val booksRepository: BooksRepository = BooksRepository(ApiProvider.booksApi())

    //////////////////////////////////////////
    // MUTABLE-STATES AND OBSERVABLE STATES
    //////////////////////////////////////////
    private var _queryText = mutableStateOf("")
    val queryText: State<String> = _queryText


    private var _book = mutableStateOf(Constants.fakeBook)
    val book: State<Item> = _book

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    //////////////////////////////////////////
    // FUNCTIONS
    //////////////////////////////////////////
    fun setBook(book: Item) {
        _book.value = book
    }

    fun setQueryText(query: String) {
        _queryText.value = query
    }


    fun onSearch() {
        _searchState.value = SearchState(searchOperation = SearchOperation.LOADING)
        viewModelScope.launch {
            _searchState.value = SearchState(
                data = Pager(
                    config = PagingConfig(pageSize = 10, prefetchDistance = 5),
                    pagingSourceFactory = {
                        BookSource(
                            booksRepository = booksRepository,
                            paginateData = Paginate(
                                query = _queryText.value,
                            )
                        )
                    }
                ).flow.cachedIn(viewModelScope),
                searchOperation = SearchOperation.DONE
            )
        }
    }


    ////////////////////

    private val amplifyService: AmplifyService = AmplifyServiceImpl()

    lateinit var navigateTo: (String) -> Unit

    var loginState = mutableStateOf(LoginState())
        private set

    var signUpState = mutableStateOf(SignUpState())
        private set

    var verificationCodeState = mutableStateOf(VerificationCodeState())
        private set

    fun updateSignUpState(username: String? = null, email: String? = null, password: String? = null) {
        username?.let {
            signUpState.value = signUpState.value.copy(username = it)
            verificationCodeState.value = verificationCodeState.value.copy(username = it)
        }
        email?.let { signUpState.value = signUpState.value.copy(email = it) }
        password?.let { signUpState.value = signUpState.value.copy(password = it) }
    }

    fun updateLoginState(username: String? = null, password: String? = null) {
        username?.let { loginState.value = loginState.value.copy(username = it) }
        password?.let { loginState.value = loginState.value.copy(password = it) }
    }

    fun updateVerificationCodeState(code: String) {
        verificationCodeState.value = verificationCodeState.value.copy(code = code)
    }

    fun configureAmplify(context: Context) {
        amplifyService.configureAmplify(context)
    }

    fun showSignUp() {
        navigateTo("signUp")
    }

    fun showLogin() {
        navigateTo("login")
    }

    fun signUp() {
        amplifyService.signUp(signUpState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("verify")
            }
        }
    }

    fun verifyCode() {
        amplifyService.verifyCode(verificationCodeState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("login")
            }
        }
    }

    fun login() {
        amplifyService.login(loginState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("session")
            }
        }
    }

    fun logOut() {
        amplifyService.logOut {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("login")
            }
        }
    }


}