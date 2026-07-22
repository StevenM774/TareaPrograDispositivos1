package com.ucenm.tareaprogradispositivos1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    @GET("GetPersons.php")
    Call<List<Persona>> getPersons();

    @POST("PostPersons.php")
    Call<Persona> createPerson(@Body Persona persona);

    @PUT("UpdatePersons.php")
    Call<Persona> updatePerson(@Body Persona persona);

    @HTTP(method = "DELETE", path = "DeletePersons.php", hasBody = true)
    Call<Void> deletePerson(@Body Persona persona);
}
