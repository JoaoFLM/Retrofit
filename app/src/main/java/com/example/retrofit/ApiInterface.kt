import com.example.retrofit.Response.CotacaoResponse
import com.example.retrofit.Response.DividendoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {
    @GET("/companies/{cvm_code}/dividends")
    fun getDividendo(): Call<DividendoResponse>

    @GET("/tickers/{ticker}/quotes")
     fun getCotacao(
         @Header("Authorization") authtoken: String
     ): Call<CotacaoResponse>

}