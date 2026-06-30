$(document).ready(function() {
$('#deleteButton').on('click', function() {
var form = $('#deleteForm');
var action = form.attr('action');
Swal.fire({
title: '¿Estás seguro de que deseas eliminar esta película?',
text: "¡No podrás revertir esto!",
icon: 'warning',
showCancelButton: true,
confirmButtonColor: '#3085d6',
cancelButtonColor: '#d33',
confirmButtonText: '¡Sí, eliminarla!'
}).then((result) => {
if (result.isConfirmed) {
$.ajax({
url: action,
type: 'POST',
data: form.serialize(),
success: function(response) {
Swal.fire({
title: 'Eliminada!',
text: 'La película ha sido eliminada.',
icon: 'success',
timer: 2000,
timerProgressBar: true,
willClose: () => {
window.location.href = '/'; // Redirige después de cerrar 
SweetAlert
}
});
},
error: function(xhr) {
Swal.fire(
'Error',
'Hubo un problema al eliminar la película.',
'error'
);
}
});
}
});
});
});