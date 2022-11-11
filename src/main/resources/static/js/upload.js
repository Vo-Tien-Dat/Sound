// UPLOAD AVATAR
const avatarFile = document.getElementById("file_image");

const previewContainer = document.getElementById("avatar-preview");

const previewImage = previewContainer.querySelector(".avatar-preview__image");

previewImage.style.display = "none";
//feature: use to remove image
previewContainer.addEventListener("click", function () {
  const file = avatarFile.files[0];
  if (file != undefined) {
    previewImage.removeAttribute("src");
    previewImage.style.display = "none";
  }
});

//feature: use to upload image
avatarFile.addEventListener("change", function () {
  const file = this.files[0];

  if (file) {
    previewImage.style.display = "inline";

    const reader = new FileReader();

    reader.addEventListener("load", function () {
      previewImage.setAttribute("src", this.result);
    });
    reader.readAsDataURL(file);
  }
});
