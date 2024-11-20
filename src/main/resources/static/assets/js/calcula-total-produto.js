$(document).ready(function() {
console.log('Script executado');
    function updateValorFinal() {
        const valorOriginal = parseFloat($('#valorOriginal').val()) || 0;
        const valorDesconto = parseFloat($('#valorDesconto').val()) || 0;
        const valorFinal = valorOriginal - valorDesconto;
        $('#valorFinal').val(valorFinal.toFixed(2));
    }
    $('#valorOriginal, #valorDesconto').on('input', updateValorFinal);
    updateValorFinal();
});