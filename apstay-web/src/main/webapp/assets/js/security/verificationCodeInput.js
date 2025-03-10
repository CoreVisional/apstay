document.addEventListener('DOMContentLoaded', function() {
    
    const form = document.getElementById('verificationForm');
    
    if (!form) {
        return;
    }
    
    const inputs = document.querySelectorAll('#digit1, #digit2, #digit3, #digit4, #digit5, #digit6');

    let hiddenInput = document.getElementById('verificationCode');
    if (!hiddenInput) {
        hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.id = 'verificationCode';
        hiddenInput.name = 'verificationCode';
        form.appendChild(hiddenInput);
    }

    function checkAndSubmit() {
        let code = '';
        let isComplete = true;
        
        inputs.forEach(input => {
            code += input.value;
            if (input.value.length === 0) {
                isComplete = false;
            }
        });
        
        hiddenInput.value = code;

        if (isComplete && code.length === 6) {
            form.submit();
        }
    }

    inputs.forEach((input, index) => {
        input.addEventListener('keypress', function(e) {
            const charCode = (e.which) ? e.which : e.keyCode;
            if (charCode < 48 || charCode > 57) {
                e.preventDefault();
                return false;
            }
        });

        input.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');

            if (this.value.length === this.maxLength) {
                const nextInput = inputs[index + 1];
                if (nextInput) {
                    nextInput.focus();
                } else {
                    checkAndSubmit();
                }
            }
        });

        input.addEventListener('keydown', function(e) {
            if (e.key === 'Backspace' && this.value.length === 0) {
                const prevInput = inputs[index - 1];
                if (prevInput) {
                    prevInput.focus();
                }
            }
        });
    });

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        let code = '';
        inputs.forEach(input => {
            code += input.value;
        });

        if (code.length !== 6) {
            alert('Please enter a complete 6-digit verification code.');
            return;
        }

        hiddenInput.value = code;
        this.submit();
    });

    inputs[0].addEventListener('paste', function(e) {
        e.preventDefault();
        const pastedData = e.clipboardData.getData('text').trim();

        if (/^\d{1,6}$/.test(pastedData)) {
            for (let i = 0; i < Math.min(6, pastedData.length); i++) {
                inputs[i].value = pastedData[i];
            }

            if (pastedData.length < 6) {
                inputs[pastedData.length].focus();
            } else {
                inputs[5].focus();
                checkAndSubmit();
            }
        }
    });
});