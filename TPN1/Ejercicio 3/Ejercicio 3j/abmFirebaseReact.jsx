import React, { useState, useEffect } from 'react';
import { db } from './firebase';
import {
  collection,
  getDocs,
  addDoc,
  updateDoc,
  deleteDoc,
  doc
} from 'firebase/firestore';

function App() {
  const [productos, setProductos] = useState([]);
  const [nombre, setNombre] = useState('');
  const [categoria, setCategoria] = useState('');
  const [idEditando, setIdEditando] = useState(null);
  const [error, setError] = useState('');

  const coleccionRef = collection(db, 'inventario');

  const obtenerProductos = async () => {
    try {
      const snapshot = await getDocs(coleccionRef);
      setProductos(snapshot.docs.map((item) => ({ ...item.data(), id: item.id })));
      setError('');
    } catch (err) {
      setError('No se pudo conectar con Firestore. Verificá la configuración de Firebase.');
    }
  };

  useEffect(() => {
    obtenerProductos();
  }, []);

  const guardarProducto = async (evento) => {
    evento.preventDefault();
    const nombreLimpio = nombre.trim();
    const categoriaLimpia = categoria.trim();

    if (!nombreLimpio || !categoriaLimpia) {
      setError('Completa nombre y categoría antes de guardar.');
      return;
    }

    try {
      if (idEditando === null) {
        await addDoc(coleccionRef, { nombre: nombreLimpio, categoria: categoriaLimpia });
      } else {
        const productoRef = doc(db, 'inventario', idEditando);
        await updateDoc(productoRef, { nombre: nombreLimpio, categoria: categoriaLimpia });
        setIdEditando(null);
      }

      setNombre('');
      setCategoria('');
      setError('');
      await obtenerProductos();
    } catch (err) {
      setError('La operación no pudo completarse. Revisá la conexión a Firestore.');
    }
  };

  const seleccionarParaEditar = (producto) => {
    setIdEditando(producto.id);
    setNombre(producto.nombre);
    setCategoria(producto.categoria);
    setError('');
  };

  const eliminarProducto = async (id) => {
    try {
      const productoRef = doc(db, 'inventario', id);
      await deleteDoc(productoRef);
      await obtenerProductos();
    } catch (err) {
      setError('No se pudo eliminar el producto.');
    }
  };

  return (
    <div style={{ maxWidth: '700px', margin: '40px auto', fontFamily: 'sans-serif' }}>
      <h2>Inventario de productos</h2>

      <form onSubmit={guardarProducto} style={{ marginBottom: '20px' }}>
        <input
          type="text"
          placeholder="Nombre del producto"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          style={{ marginRight: '10px', padding: '8px', width: '240px' }}
        />
        <input
          type="text"
          placeholder="Categoría"
          value={categoria}
          onChange={(e) => setCategoria(e.target.value)}
          style={{ marginRight: '10px', padding: '8px', width: '200px' }}
        />
        <button type="submit" style={{ padding: '8px 16px' }}>
          {idEditando ? 'Guardar cambios' : 'Agregar'}
        </button>
      </form>

      {error && (
        <p style={{ color: '#b42318', marginBottom: '12px' }}>{error}</p>
      )}

      <table border="1" cellPadding="10" cellSpacing="0" style={{ width: '100%', textAlign: 'left' }}>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {productos.map((producto) => (
            <tr key={producto.id}>
              <td>{producto.nombre}</td>
              <td>{producto.categoria}</td>
              <td>
                <button onClick={() => seleccionarParaEditar(producto)} style={{ marginRight: '5px' }}>
                  Editar
                </button>
                <button onClick={() => eliminarProducto(producto.id)}>
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;