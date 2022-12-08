// UPLOAD AVATAR
const avatarFile = document.getElementById("file_image");

const previewContainer = document.getElementById("avatar-preview");

const previewImage = previewContainer.querySelector(".avatar-preview__image");

const labelUploadFileAudio = document.getElementById("label_upload_file_audio");

const messageNameSound = document.getElementById("message_name_sound");

const messageNameSinger = document.getElementById("message_name_singer");

const buttonAdd = document.getElementById("button_add");

const idPreviewAvatar = document.getElementById("id_preview_avatar");

const urlCurrent = window.location.href;

const urlUserDefault = "/assets/img/default/user_default.png";

const urlPlaylistDefault = "/assets/img/default/sound_default.png";

const urlAlbumDefault = "/assets/img/default/sound_default.png";

const urlSoundDefault = "/assets/img/default/sound_default.png";

if (previewImage.getAttribute("src") == "") {
  let urlImageDefault = null;
  const items = [
    {
      id: "user",
      url: "/assets/img/default/user_default.png",
    },
    { id: "playlist", url: "/assets/img/default/sound_default.png" },
    {
      id: "album",
      url: "/assets/img/default/sound_default.png",
    },
    {
      id: "sound",
      url: "/assets/img/default/sound_default.png",
    },
  ];
  items.map((value, index) => {
    if (urlCurrent.search(value.id) !== -1) {
      urlImageDefault = value.url;
    }
  });

  idPreviewAvatar.src = urlImageDefault;
}

//feature: use to remove image
previewContainer.addEventListener("click", function () {
  let sizeFile = avatarFile.files.length;
  if (sizeFile != 0) {
    avatarFile.value = "";
  }
  let urlImageDefault = null;
  const items = [
    {
      id: "user",
      url: "/assets/img/default/user_default.png",
    },
    { id: "playlist", url: "/assets/img/default/sound_default.png" },
    {
      id: "album",
      url: "/assets/img/default/sound_default.png",
    },
    {
      id: "sound",
      url: "/assets/img/default/sound_default.png",
    },
  ];
  items.map((value, index) => {
    if (urlCurrent.search(value.id) !== -1) {
      urlImageDefault = value.url;
    }
  });

  idPreviewAvatar.src = urlImageDefault;
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

// if (fileAudio != null) {
//   const numberFile = fileAudio.files.length;
//   if (numberFile == 0) {
//     labelUploadFileAudio.classList.add("bg-info");
//   }

//   //listen event change upload file
//   fileAudio.addEventListener("change", function () {
//     const numberFile = this.files.length;
//     if (numberFile == 0) {
//       labelUploadFileAudio.classList.add("bg-info");
//     } else {
//       labelUploadFileAudio.classList.add("bg-danger");
//     }
//   });
// }

// xử lí sự kiện submit sau khi upload ảnh

const formUpdateImage = document.getElementById("form_update_image");

if (formUpdateImage != null) {
  avatarFile.addEventListener("change", function () {
    const filesLength = this.files.length;
    if (filesLength != 0) {
      formUpdateImage.submit();
    }
  });
}
