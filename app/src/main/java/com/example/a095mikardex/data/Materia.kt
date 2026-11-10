package com.example.mikardex.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Calificación mínima aprobatoria (si tu Tec usa otra, cámbiala aquí y en MateriaDao). */
const val CALIFICACION_MINIMA = 70

enum class TipoCurso(val etiqueta: String) {
    NORMAL("Normal"),
    REPETICION("Repetición"),
    ESPECIAL("Especial")
}

/**
 * ÚNICA tabla de la base de datos: una fila por materia de la retícula.
 * semestre 1..9 = semestres, semestre 10 = requisitos complementarios.
 */
@Entity(tableName = "materia")
data class Materia(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val semestre: Int,
    val creditos: Int,
    val calNormal: Int? = null,
    val calRepeticion: Int? = null,
    val calEspecial: Int? = null
)

/** Calificación vigente: la última oportunidad que tenga calificación (Especial > Repetición > Normal). */
val Materia.calificacionFinal: Int?
    get() = calEspecial ?: calRepeticion ?: calNormal

val Materia.tipoCurso: TipoCurso?
    get() = when {
        calEspecial != null -> TipoCurso.ESPECIAL
        calRepeticion != null -> TipoCurso.REPETICION
        calNormal != null -> TipoCurso.NORMAL
        else -> null
    }

val Materia.aprobada: Boolean
    get() = (calificacionFinal ?: 0) >= CALIFICACION_MINIMA

fun Materia.calificacionDe(tipo: TipoCurso): Int? = when (tipo) {
    TipoCurso.NORMAL -> calNormal
    TipoCurso.REPETICION -> calRepeticion
    TipoCurso.ESPECIAL -> calEspecial
}
