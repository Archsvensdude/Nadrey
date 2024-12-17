function displayFileName(input, targetId) {
    const fileName = input.files[0]?.name || "";
    document.getElementById(targetId).value = fileName;
}