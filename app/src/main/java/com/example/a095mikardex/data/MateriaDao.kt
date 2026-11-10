package com.example.mikardex.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Calificación vigente en SQL: COALESCE(calEspecial, calRepeticion, calNormal)
 * Aprobado = >= 70.  El promedio general incluye TODAS las materias, también los requisitos complementarios (semestre 10).
 */
@Dao
interface MateriaDao {

    @Query("SELECT * FROM materia WHERE semestre = :semestre ORDER BY id")
    fun materiasDelSemestre(semestre: Int): Flow<List<Materia>>

    @Query("SELECT COUNT(*) FROM materia")
    suspend fun contar(): Int

    @Insert
    suspend fun insertarTodas(materias: List<Materia>)

    @Update
    suspend fun actualizar(materia: Materia)

    // ---- Consultas de promedio ----
    @Query("SELECT AVG(COALESCE(calEspecial, calRepeticion, calNormal)) FROM materia")
    fun promedioGeneral(): Flow<Double?>

    @Query("SELECT AVG(COALESCE(calEspecial, calRepeticion, calNormal)) FROM materia WHERE semestre = :semestre")
    fun promedioDelSemestre(semestre: Int): Flow<Double?>

    // ---- Consultas de créditos ----
    @Query("SELECT COALESCE(SUM(creditos), 0) FROM materia WHERE semestre = :semestre")
    fun creditosTotalesDelSemestre(semestre: Int): Flow<Int>

    @Query("SELECT COALESCE(SUM(creditos), 0) FROM materia WHERE semestre = :semestre AND COALESCE(calEspecial, calRepeticion, calNormal) >= 70")
    fun creditosObtenidosDelSemestre(semestre: Int): Flow<Int>

    @Query("SELECT COALESCE(SUM(creditos), 0) FROM materia")
    fun creditosTotalesCarrera(): Flow<Int>

    @Query("SELECT COALESCE(SUM(creditos), 0) FROM materia WHERE COALESCE(calEspecial, calRepeticion, calNormal) >= 70")
    fun creditosObtenidosCarrera(): Flow<Int>
}
