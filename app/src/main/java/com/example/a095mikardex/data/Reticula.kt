package com.example.mikardex.data

/**
 * Retícula de Ingeniería en Sistemas Computacionales (nombre y créditos de cada materia).
 * Se insertan en la base de datos la primera vez que se abre la app.
 *
 * Las calificaciones del 1er semestre son DE EJEMPLO (para la demostración): edítalas desde la app
 * o aquí mismo antes de la primera ejecución (si ya corriste la app, borra sus datos o desinstálala).
 */
object Reticula {

    val nombresSemestre = listOf(
        "1er semestre", "2do semestre", "3er semestre", "4to semestre", "5to semestre",
        "6to semestre", "7mo semestre", "8vo semestre", "9no semestre", "Requisitos complementarios"
    )

    private fun m(
        nombre: String, semestre: Int, creditos: Int,
        normal: Int? = null, repeticion: Int? = null, especial: Int? = null
    ) = Materia(
        nombre = nombre, semestre = semestre, creditos = creditos,
        calNormal = normal, calRepeticion = repeticion, calEspecial = especial
    )

    val materias: List<Materia> = listOf(
        // 1er semestre - 27 créditos (con calificaciones de ejemplo)
        m("Cálculo Diferencial", 1, 5, normal = 55, repeticion = 72),
        m("Fundamentos de Investigación", 1, 4, normal = 90),
        m("Fundamentos de Programación", 1, 5, normal = 95),
        m("Matemáticas Discretas", 1, 5, normal = 50, repeticion = 60, especial = 75),
        m("Taller de Administración", 1, 4, normal = 88),
        m("Taller de Ética", 1, 4, normal = 92),
        m("Tutoría I", 1, 0, normal = 100),

        // 2do semestre - 33
        m("Algebra Lineal", 2, 5),
        m("Cálculo Integral", 2, 5),
        m("Contabilidad Financiera", 2, 4),
        m("Desarrollo Sustentable", 2, 5),
        m("Física General", 2, 5),
        m("Programación Orientada a Objetos", 2, 5),
        m("Química", 2, 4),
        m("Tutoría II", 2, 0),

        // 3er semestre - 33
        m("Cálculo Vectorial", 3, 5),
        m("Estructura de Datos", 3, 5),
        m("Fundamentos de Base de Datos", 3, 5),
        m("Investigación de Operaciones", 3, 4),
        m("Principios Eléctricos y Aplicaciones Digitales", 3, 5),
        m("Probabilidad y Estadística", 3, 5),
        m("Taller de Sistemas Operativos", 3, 4),

        // 4to semestre - 32
        m("Arquitectura de Computadoras", 4, 5),
        m("Ecuaciones Diferenciales", 4, 5),
        m("Fundamentos de Telecomunicaciones", 4, 4),
        m("Métodos Numéricos", 4, 4),
        m("Simulación", 4, 5),
        m("Taller de Base de Datos", 4, 4),
        m("Tópicos Avanzados de Programación", 4, 5),

        // 5to semestre - 28
        m("Administración de Base de Datos", 5, 5),
        m("Fundamentos de Ingeniería de Software", 5, 4),
        m("Lenguajes y Autómatas I", 5, 5),
        m("Programación Web", 5, 5),
        m("Redes de Computadoras", 5, 5),
        m("Sistemas Operativos", 5, 4),

        // 6to semestre - 27
        m("Conmutación y Enrutamiento de Redes de Datos", 6, 5),
        m("Cultura Empresarial", 6, 4),
        m("Graficación", 6, 4),
        m("Ingeniería de Software", 6, 5),
        m("Lenguajes de Interfaz", 6, 4),
        m("Lenguajes y Autómatas II", 6, 5),

        // 7mo semestre - 32
        m("Administración de Redes", 7, 4),
        m("E-Business", 7, 5),
        m("Gestión de Proyectos de Software", 7, 6),
        m("Programación Lógica y Funcional", 7, 4),
        m("Propuesta de Proyectos y Planes Tecnológicos", 7, 5),
        m("Sistemas Programables", 7, 4),
        m("Taller de Investigación I", 7, 4),

        // 8vo semestre - 23
        m("Desarrollo Web Avanzado", 8, 5),
        m("Inteligencia Artificial", 8, 4),
        m("Programación para Dispositivos Móviles para Android", 8, 5),
        m("Programación para Dispositivos Móviles para iOS", 8, 5),
        m("Taller de Investigación II", 8, 4),

        // 9no semestre - 10
        m("Residencia Profesional", 9, 10),

        // Requisitos complementarios - 15
        m("Actividades Complementarias", 10, 5),
        m("Servicio Social", 10, 10)
    )
}
