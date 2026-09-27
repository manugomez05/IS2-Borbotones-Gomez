const formularioRegistro = document.getElementById("formularioRegistro");
const formularioInicio = document.getElementById("formularioInicio");
const mensajeResultado = document.getElementById("mensajeResultado");
const pestanaRegistro = document.getElementById("pestanaRegistro");
const pestanaInicio = document.getElementById("pestanaInicio");
const claveUsuario = "usuarioRegistradoGomez";

function mostrarError(id, mensaje) {
  const campo = document.getElementById(id);
  if (campo) campo.textContent = mensaje;
}

function limpiarErrores() {
  document.querySelectorAll(".error").forEach((elemento) => {
    elemento.textContent = "";
  });
  mensajeResultado.textContent = "";
  mensajeResultado.className = "mensaje";
}

function mostrarFormulario(formularioActivo) {
  const esRegistro = formularioActivo === formularioRegistro;
  formularioRegistro.classList.toggle("oculto", !esRegistro);
  formularioInicio.classList.toggle("oculto", esRegistro);
  pestanaRegistro.classList.toggle("activa", esRegistro);
  pestanaInicio.classList.toggle("activa", !esRegistro);
  pestanaRegistro.setAttribute("aria-selected", String(esRegistro));
  pestanaInicio.setAttribute("aria-selected", String(!esRegistro));
  document.getElementById("tituloFormulario").textContent = esRegistro ? "Crear una cuenta" : "Bienvenido nuevamente";
  document.getElementById("descripcionFormulario").textContent = esRegistro ? "Completá tus datos para registrarte." : "Ingresá tus datos para iniciar sesión.";
  limpiarErrores();
}

function validarEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function guardarUsuario(email, password, nombre) {
  const usuario = { nombre, email, password, creadoEn: new Date().toISOString() };
  localStorage.setItem(claveUsuario, JSON.stringify(usuario));
}

formularioRegistro.addEventListener("submit", (evento) => {
  evento.preventDefault();
  limpiarErrores();

  const nombre = document.getElementById("nombre").value.trim();
  const email = document.getElementById("emailRegistro").value.trim();
  const password = document.getElementById("passwordRegistro").value;
  const confirmarPassword = document.getElementById("confirmarPassword").value;
  const aceptaTerminos = document.getElementById("aceptaTerminos").checked;
  let valido = true;

  if (nombre.length < 2) {
    mostrarError("errorNombre", "Ingresá tu nombre completo.");
    valido = false;
  }

  if (!validarEmail(email)) {
    mostrarError("errorEmailRegistro", "Ingresá un correo electrónico válido.");
    valido = false;
  }

  if (password.length < 6) {
    mostrarError("errorPasswordRegistro", "La contraseña debe tener al menos 6 caracteres.");
    valido = false;
  }

  if (password !== confirmarPassword) {
    mostrarError("errorConfirmarPassword", "Las contraseñas no coinciden.");
    valido = false;
  }

  if (!aceptaTerminos) {
    mostrarError("errorTerminos", "Debés aceptar los términos y condiciones.");
    valido = false;
  }

  if (!valido) {
    return;
  }

  guardarUsuario(email, password, nombre);
  mensajeResultado.textContent = "¡Registro completado correctamente!";
  mensajeResultado.className = "mensaje exito";
  formularioRegistro.reset();
});

formularioInicio.addEventListener("submit", (evento) => {
  evento.preventDefault();
  limpiarErrores();

  const email = document.getElementById("emailInicio").value.trim();
  const password = document.getElementById("passwordInicio").value;
  const usuarioGuardado = JSON.parse(localStorage.getItem(claveUsuario) || "null");
  let valido = true;

  if (!validarEmail(email)) {
    mostrarError("errorEmailInicio", "Ingresá un correo electrónico válido.");
    valido = false;
  }

  if (!password) {
    mostrarError("errorPasswordInicio", "Ingresá tu contraseña.");
    valido = false;
  }

  if (!valido) {
    return;
  }

  if (!usuarioGuardado || usuarioGuardado.email !== email || usuarioGuardado.password !== password) {
    mensajeResultado.textContent = "El correo o la contraseña son incorrectos.";
    mensajeResultado.className = "mensaje errorSesion";
    return;
  }

  mensajeResultado.textContent = "¡Inicio de sesión exitoso!";
  mensajeResultado.className = "mensaje exito";
  formularioInicio.reset();
});

pestanaRegistro.addEventListener("click", () => mostrarFormulario(formularioRegistro));
pestanaInicio.addEventListener("click", () => mostrarFormulario(formularioInicio));
