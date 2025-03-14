document.addEventListener('DOMContentLoaded', function() {
    const scanButton = document.getElementById('scanButton');
    const scannerModal = document.getElementById('scannerModal');
    const video = document.getElementById('scanner');
    const errorDiv = document.getElementById('scanner-error');
    const errorMessage = document.getElementById('error-message');
    
    let scanning = false;
    let videoStream = null;

    // Show the scanner modal when the scan button is clicked
    if (scanButton) {
        scanButton.addEventListener('click', function() {
            $('#scannerModal').modal('show');
        });
    }
    
    // Start the scanner when the modal is shown
    $('#scannerModal').on('shown.bs.modal', function() {
        startScanner();
    });
    
    // Stop the scanner when the modal is hidden
    $('#scannerModal').on('hidden.bs.modal', function() {
        stopScanner();
    });
    
    function startScanner() {
        // Check for camera support
        if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
            showError('Camera access is not supported by your browser');
            return;
        }
        
        scanning = true;
        
        // Request camera access
        navigator.mediaDevices.getUserMedia({ 
            video: { facingMode: "environment" } 
        })
        .then(function(stream) {
            videoStream = stream;
            video.srcObject = stream;
            video.setAttribute('playsinline', true);
            video.play();
            requestAnimationFrame(tick);
        })
        .catch(function(err) {
            console.error('Error accessing camera:', err);
            showError('Could not access camera: ' + err.message);
        });
    }
    
    function stopScanner() {
        scanning = false;
        if (videoStream) {
            videoStream.getTracks().forEach(track => track.stop());
            videoStream = null;
        }
    }
    
    function showError(message) {
        errorMessage.textContent = message;
        errorDiv.classList.remove('d-none');
    }
    
    function tick() {
        if (!scanning) return;
        
        if (video.readyState === video.HAVE_ENOUGH_DATA) {
            const canvas = document.createElement('canvas');
            const context = canvas.getContext('2d');
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            context.drawImage(video, 0, 0, canvas.width, canvas.height);
            
            const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
            const code = jsQR(imageData.data, imageData.width, imageData.height, {
                inversionAttempts: "dontInvert",
            });
            
            if (code) {
                const verificationCode = code.data.trim();
                
                if (/^\d{6}$/.test(verificationCode)) {
                    fillVerificationInputs(verificationCode);
                    $('#scannerModal').modal('hide');
                } else {
                    showError('Invalid verification code format');
                    requestAnimationFrame(tick);
                }
            } else {
                requestAnimationFrame(tick);
            }
        } else {
            requestAnimationFrame(tick);
        }
    }
    
    function fillVerificationInputs(code) {
        for (let i = 0; i < 6; i++) {
            const digitInput = document.getElementById(`digit${i+1}`);
            if (digitInput) {
                digitInput.value = code.charAt(i);
            }
        }
        const form = document.getElementById('verificationForm');
        if (form) {
            form.dispatchEvent(new Event('submit'));
        }
    }
});
