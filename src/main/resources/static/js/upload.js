// UPLOAD AVATAR
const avatarFile = document.getElementById("file_image");

const previewContainer = document.getElementById("avatar-preview");

const previewImage = previewContainer.querySelector(".avatar-preview__image");

const fileAudio = document.getElementById("file_audio");

const labelUploadFileAudio = document.getElementById("label_upload_file_audio");

const inputNameSound = document.getElementById("input_name_sound");

const inputNameSinger = document.getElementById("input_name_singer");

const messageNameSound = document.getElementById("message_name_sound");

const messageNameSinger = document.getElementById("message_name_singer");

const buttonAdd = document.getElementById("button_add");

//

if (previewImage.getAttribute("src") == "") {
  previewImage.style.display = "none";
}

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

// UPLOAD AUDIO

if (fileAudio != null) {
  // chdck file is empty

  const numberFile = fileAudio.files.length;
  if (numberFile == 0) {
    labelUploadFileAudio.classList.add("bg-info");
  }

  //listen event change upload file
  fileAudio.addEventListener("change", function () {
    const numberFile = this.files.length;
    if (numberFile == 0) {
      labelUploadFileAudio.classList.add("bg-info");
    } else {
      labelUploadFileAudio.classList.add("bg-danger");
    }
  });
}

// check field text is null in form add sound

if (buttonAdd != null) {
  buttonAdd.addEventListener("click", function (event) {
    let nameSound = inputNameSound.value;
    let nameSinger = inputNameSinger.value;
    if (nameSound === "") {
      messageNameSound.classList.remove("d-none");
    }
    if (nameSinger === "") {
      messageNameSinger.classList.remove("d-none");
    }

    if (nameSound == "" || nameSinger == "") {
      event.preventDefault();
    }
  });
}
