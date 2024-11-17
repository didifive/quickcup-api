/**
 * Script para Modal e PopOver Bootstrap 5.3.3
 */

/**
 * Modal Excluir
 */
let modalExcluir = document.getElementById('modalExcluir')
modalExcluir.addEventListener('show.bs.modal', function (event) {
	let button = event.relatedTarget;
	let modalObject = button.getAttribute('data-modal-object');
	let modalName = button.getAttribute('data-modal-name');
	let modalLink = button.getAttribute('data-modal-link');

	let modalTitle = modalExcluir.querySelector('.modal-title')
	let modalBody = modalExcluir.querySelector('.modal-body')
	let modalActionButton = modalExcluir.querySelector('.btn-secondary')

	modalTitle.textContent = 'Excluir ' + modalObject + ' ' + modalName
	modalBody.textContent = 'Deseja realmente excluir ' + modalObject + ' ' + modalName + '?';
	modalActionButton.href = modalLink;	 
})

/**
 * Modal Cancelar
 */
let modalCancelar = document.getElementById('modalCancelar')
modalCancelar.addEventListener('show.bs.modal', function (event) {
	let button = event.relatedTarget;
	let modalObject = button.getAttribute('data-modal-object');
	let modalName = button.getAttribute('data-modal-name');
	let modalLink = button.getAttribute('data-modal-link');

	let modalTitle = modalCancelar.querySelector('.modal-title')
	let modalBody = modalCancelar.querySelector('.modal-body')
	let modalActionButton = modalCancelar.querySelector('.btn-danger')

	modalTitle.textContent = 'Cancelar ' + modalObject + ' ' + modalName
	modalBody.textContent = 'Deseja realmente cancelar ' + modalObject + ' ' + modalName + '?';
	modalActionButton.href = modalLink;
})

/**
 * PopOver
 */
let popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
let popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
	return new bootstrap.Popover(popoverTriggerEl)
})