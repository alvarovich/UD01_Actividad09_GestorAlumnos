// Gestor de alumnos - UD01 Actividad 09 (PMDM)

data class Alumno(
    val nombre: String,
    val apellidos: String,
    val nota: Double
)

fun main() {
    val alumnos = mutableListOf<Alumno>()
    var salir = false

    while (!salir) {
        mostrarMenu()
        when (readLine()?.trim()) {
            "1" -> registrarAlumno(alumnos)
            "2" -> listarAlumnos(alumnos)
            "3" -> {
                if (alumnos.isEmpty()) {
                    println("No hay alumnos registrados. No se puede calcular la media.")
                } else {
                    println("La media del grupo es: ${"%.2f".format(calcularMedia(alumnos))}")
                }
            }
            "4" -> mostrarAprobados(alumnos)
            "5" -> mostrarSuspensos(alumnos)
            "6" -> buscarAlumno(alumnos)
            "7" -> eliminarAlumno(alumnos)
            "0" -> {
                println("Saliendo del programa. ¡Hasta pronto!")
                salir = true
            }
            else -> println("Opción no válida. Introduce un número del 1 al 8.")
        }
        println()
    }
}

fun mostrarMenu() {
    println("===== GESTOR DE ALUMNOS =====")
    println("1. Registrar alumno")
    println("2. Listar alumnos")
    println("3. Calcular media del grupo")
    println("4. Mostrar aprobados")
    println("5. Mostrar suspensos")
    println("6. Buscar alumno")
    println("7. Eliminar alumno")
    println("0. Salir")
    print("Seleccione una opción: ")
}

fun registrarAlumno(alumnos: MutableList<Alumno>) {
    print("Introduce el nombre: ")
    val nombre = readLine()?.trim() ?: ""
    if (nombre.isEmpty()) {
        println("Error: el nombre no puede estar vacío.")
        return
    }

    print("Introduce los apellidos: ")
    val apellidos = readLine()?.trim() ?: ""
    if (apellidos.isEmpty()) {
        println("Error: los apellidos no pueden estar vacíos.")
        return
    }

    print("Introduce la nota final: ")
    // Se admite coma o punto como separador decimal
    val nota = readLine()?.trim()?.replace(',', '.')?.toDoubleOrNull()
    if (nota == null) {
        println("Error: la nota debe ser un valor numérico.")
        return
    }
    if (nota < 0.0 || nota > 10.0) {
        println("Error: la nota debe estar entre 0 y 10.")
        return
    }

    alumnos.add(Alumno(nombre, apellidos, nota))
    println("Alumno registrado correctamente.")
}

fun listarAlumnos(alumnos: List<Alumno>) {
    if (alumnos.isEmpty()) {
        println("No hay alumnos registrados.")
        return
    }
    println("===== LISTADO DE ALUMNOS =====")
    alumnos.forEachIndexed { indice, alumno ->
        println("${indice + 1}. ${alumno.nombre} ${alumno.apellidos} - Nota: ${alumno.nota}")
    }
}

fun calcularMedia(alumnos: List<Alumno>): Double {
    if (alumnos.isEmpty()) return 0.0
    return alumnos.sumOf { it.nota } / alumnos.size
}

fun mostrarAprobados(alumnos: List<Alumno>) {
    val aprobados = alumnos.filter { it.nota >= 5.0 }
    println("===== ALUMNOS APROBADOS =====")
    if (aprobados.isEmpty()) {
        println("No hay alumnos aprobados.")
    } else {
        aprobados.forEach { println("${it.nombre} ${it.apellidos} - Nota: ${it.nota}") }
    }
}

fun mostrarSuspensos(alumnos: List<Alumno>) {
    val suspensos = alumnos.filter { it.nota < 5.0 }
    println("===== ALUMNOS SUSPENSOS =====")
    if (suspensos.isEmpty()) {
        println("No hay alumnos suspensos.")
    } else {
        suspensos.forEach { println("${it.nombre} ${it.apellidos} - Nota: ${it.nota}") }
    }
}

fun buscarAlumno(alumnos: List<Alumno>) {
    print("Introduce el texto a buscar: ")
    val texto = readLine()?.trim() ?: ""
    if (texto.isEmpty()) {
        println("Error: el texto de búsqueda no puede estar vacío.")
        return
    }

    val encontrados = alumnos.filter {
        it.nombre.contains(texto, ignoreCase = true) ||
                it.apellidos.contains(texto, ignoreCase = true)
    }

    if (encontrados.isEmpty()) {
        println("No se han encontrado alumnos con ese criterio.")
    } else {
        println("===== RESULTADOS DE LA BÚSQUEDA =====")
        encontrados.forEach { println("${it.nombre} ${it.apellidos} - Nota: ${it.nota}") }
    }
}

fun eliminarAlumno(alumnos: MutableList<Alumno>) {
    if (alumnos.isEmpty()) {
        println("No hay alumnos registrados. No se puede eliminar ninguno.")
        return
    }

    listarAlumnos(alumnos)
    print("Introduce el número del alumno a eliminar: ")
    val posicion = readLine()?.trim()?.toIntOrNull()

    if (posicion == null) {
        println("Error: debes introducir un número entero.")
        return
    }
    if (posicion < 1 || posicion > alumnos.size) {
        println("Error: no existe ningún alumno con ese número.")
        return
    }

    alumnos.removeAt(posicion - 1)
    println("Alumno eliminado correctamente.")
}