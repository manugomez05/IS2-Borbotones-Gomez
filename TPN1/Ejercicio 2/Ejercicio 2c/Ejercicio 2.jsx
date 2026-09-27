import { useMemo, useState } from "react";

function Encabezado({ nombre }) {
  return (
    <header>
      <h1>Planificador personal</h1>
      <p>Hola, {nombre}. Organiza tus pendientes del día sin perder el foco.</p>
    </header>
  );
}

function Ejercicio2() {
  const [tareas, setTareas] = useState([
    { id: 1, texto: "Revisar objetivos del sprint", completada: true },
    { id: 2, texto: "Preparar entregable de la semana", completada: false },
    { id: 3, texto: "Actualizar documentación del proyecto", completada: false }
  ]);
  const [nuevaTarea, setNuevaTarea] = useState("");
  const [mostrarCompletadas, setMostrarCompletadas] = useState(true);

  function agregarTarea(evento) {
    evento.preventDefault();
    const texto = nuevaTarea.trim();

    if (!texto) {
      return;
    }

    setTareas((tareasActuales) => [
      ...tareasActuales,
      { id: Date.now(), texto, completada: false }
    ]);
    setNuevaTarea("");
  }

  function cambiarEstado(id) {
    setTareas((tareasActuales) =>
      tareasActuales.map((tarea) =>
        tarea.id === id ? { ...tarea, completada: !tarea.completada } : tarea
      )
    );
  }

  function eliminarTarea(id) {
    setTareas((tareasActuales) => tareasActuales.filter((tarea) => tarea.id !== id));
  }

  const tareasVisibles = useMemo(
    () => (mostrarCompletadas ? tareas : tareas.filter((tarea) => !tarea.completada)),
    [mostrarCompletadas, tareas]
  );

  const cantidadCompletadas = tareas.filter((tarea) => tarea.completada).length;

  return (
    <main>
      <Encabezado nombre="estudiante" />

      <section>
        <h2>Mi lista de pendientes</h2>
        <p>
          Completadas: {cantidadCompletadas} de {tareas.length}
        </p>

        <form onSubmit={agregarTarea}>
          <label htmlFor="nuevaTarea">Nueva tarea</label>
          <input
            id="nuevaTarea"
            type="text"
            value={nuevaTarea}
            onChange={(evento) => setNuevaTarea(evento.target.value)}
            placeholder="Escribí una tarea"
          />
          <button type="submit">Agregar</button>
        </form>

        <label>
          <input
            type="checkbox"
            checked={mostrarCompletadas}
            onChange={(evento) => setMostrarCompletadas(evento.target.checked)}
          />
          Mostrar tareas completadas
        </label>

        {tareasVisibles.length === 0 ? (
          <p>No hay tareas para mostrar.</p>
        ) : (
          <ul>
            {tareasVisibles.map((tarea) => (
              <li key={tarea.id}>
                <span>
                  {tarea.completada ? "✅" : "⬜"} {tarea.texto}
                </span>
                <button type="button" onClick={() => cambiarEstado(tarea.id)}>
                  {tarea.completada ? "Desmarcar" : "Completar"}
                </button>
                <button type="button" onClick={() => eliminarTarea(tarea.id)}>
                  Eliminar
                </button>
              </li>
            ))}
          </ul>
        )}
      </section>
    </main>
  );
}

export default Ejercicio2;
