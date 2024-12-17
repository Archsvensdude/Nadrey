function updateCharacterCount() {
    const content = document.getElementById('content');
    const charCount = document.getElementById('charCount');
    const currentLength = content.value.length;
    charCount.textContent = `글자 수: ${currentLength} / 1000`;
}