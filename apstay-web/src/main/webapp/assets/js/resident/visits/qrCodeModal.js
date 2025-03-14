document.addEventListener('DOMContentLoaded', function() {
    $('#qrCodeModal').on('show.bs.modal', function(event) {
        const button = $(event.relatedTarget);
        const code = button.data('code');
        const isActive = button.data('active');

        $('#modalVerificationCode').text(code);

        const qrContainer = document.getElementById('qrCodeContainer');
        $(qrContainer).empty();
        
        $(qrContainer).css({
            'display': 'flex',
            'justify-content': 'center',
            'align-items': 'center'
        });

        if (isActive === true || isActive === 'true') {
            try {
                new QRCode(qrContainer, {
                    text: code,
                    width: 200,
                    height: 200,
                    colorDark: "#000000",
                    colorLight: "#ffffff",
                    correctLevel: QRCode.CorrectLevel.H
                });
            } catch (error) {
                console.error('Error generating QR code:', error);
                $(qrContainer).html(
                    '<div class="alert alert-danger" role="alert">' +
                    '<i class="fas fa-exclamation-circle mr-2"></i>' +
                    'Error generating QR code. Please try again.' +
                    '</div>'
                );
            }
        } else {
            $(qrContainer).html(
                '<div class="alert alert-warning" role="alert">' +
                '<i class="fas fa-exclamation-triangle mr-2"></i>' +
                'This verification code is no longer active.' +
                '</div>'
            );
        }
    });
});