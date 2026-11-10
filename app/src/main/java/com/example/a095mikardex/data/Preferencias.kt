package com.example.mikardex.data

import android.content.Context

/**
 * Datos permanentes del alumno (NO van en la base de datos): SharedPreferences.
 * Los valores predeterminados se guardan SOLO la primera vez que se abre la app.
 * Si los cambias aquí después, borra los datos de la app (o desinstálala) para que se vuelvan a guardar.
 */
private const val NOMBRE_PREDETERMINADO = "Sergio Aaron Dorantes Martínez"
private const val CONTROL_PREDETERMINADO = "23270095"   // <-- pon aquí tu número de control
private const val PASSWORD_PREDETERMINADA = "1234"        // <-- contraseña para entrar

class Preferencias(context: Context) {

    private val prefs = context.getSharedPreferences("kardex_prefs", Context.MODE_PRIVATE)

    init {
        if (!prefs.contains(KEY_NOMBRE)) {
            prefs.edit()
                .putString(KEY_NOMBRE, NOMBRE_PREDETERMINADO)
                .putString(KEY_CONTROL, CONTROL_PREDETERMINADO)
                .putString(KEY_PASSWORD, PASSWORD_PREDETERMINADA)
                .apply()
        }
    }

    val nombre: String
        get() = prefs.getString(KEY_NOMBRE, NOMBRE_PREDETERMINADO) ?: NOMBRE_PREDETERMINADO

    val numeroControl: String
        get() = prefs.getString(KEY_CONTROL, CONTROL_PREDETERMINADO) ?: CONTROL_PREDETERMINADO

    private val password: String
        get() = prefs.getString(KEY_PASSWORD, PASSWORD_PREDETERMINADA) ?: PASSWORD_PREDETERMINADA

    var modoOscuro: Boolean
        get() = prefs.getBoolean(KEY_OSCURO, false)
        set(valor) = prefs.edit().putBoolean(KEY_OSCURO, valor).apply()

    fun credencialesValidas(control: String, contrasena: String): Boolean =
        control.trim() == numeroControl && contrasena == password

    private companion object {
        const val KEY_NOMBRE = "nombre_alumno"
        const val KEY_CONTROL = "numero_control"
        const val KEY_PASSWORD = "password"
        const val KEY_OSCURO = "modo_oscuro"
    }
}
