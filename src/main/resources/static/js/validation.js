// const inputNameAlbum = document.getElementById("input_name_album");

// const inputNameSinger = document.getElementById("input_name_singer");

const buttonAddAlbum = document.getElementById("button_add_album");

const textMessageNameAlbum = document.getElementById("text_message_name_album");

const textMessageNameSinger = document.getElementById(
  "text_message_name_singer"
);

if (buttonAddAlbum !== null) {
  buttonAddAlbum.addEventListener("click", function (e) {
    let nameAlbum = inputNameAlbum.value;
    let nameSinger = inputNameSinger.value;
    if (nameAlbum === "") {
      textMessageNameAlbum.classList.remove("d-none");
    }

    if (nameSinger === "") {
      textMessageNameSinger.classList.remove("d-none");
    }

    if (nameAlbum === "" || nameSinger === "") {
      e.preventDefault();
    }
  });
}

// playlist

const inputNamePlaylist = document.getElementById("input_name_playlist");

const buttonAddPlaylist = document.getElementById("button_add_playlist");

const textMessageNamePlaylsit = document.getElementById(
  "text_message_name_playlist"
);

if (buttonAddPlaylist !== null) {
  buttonAddPlaylist.addEventListener("click", function (e) {
    let namePlaylist = inputNamePlaylist.value;
    console.log(inputNamePlaylist);
    if (namePlaylist === "") {
      textMessageNamePlaylsit.classList.remove("d-none");
    }
    if (namePlaylist === "") {
      e.preventDefault();
    }
  });
}

// sound

const inputNameSound = document.getElementById("input_name_sound");

const textMessageNameSound = document.getElementById("text_message_name_sound");

const inputNameSinger = document.getElementById("input_name_singer");

const textMessageNameSingerSound = document.getElementById(
  "text_message_name_singer"
);

const buttonAddSound = document.getElementById("button_add_sound");

const buttonUpdateSound = document.getElementById("button_update_sound");

const inputFlexSwitchCheckDefault = document.getElementById(
  "flexSwitchCheckDefault"
);

const locationUploadFileAudio = document.getElementById(
  "location_upload_file_audio"
);

const locationShowFileAudio = document.getElementById(
  "location_show_file_audio"
);

const textFileNameAudio = document.getElementById("file_name_audio");

const fileAudio = document.getElementById("file_audio");

const deleteFileAudio = document.getElementById("delete_file_audio");

const textMessageFileAudio = document.getElementById("text_message_file_audio");

const switchUploadFile = document.getElementById("switch_upload_file");

if (fileAudio !== null) {
  fileAudio.addEventListener("change", function (e) {
    const fileNameAudio = this.files[0].name;
    textFileNameAudio.textContent = fileNameAudio;
    locationUploadFileAudio.classList.add("d-none");
    locationShowFileAudio.classList.remove("d-none");
  });
}

if (deleteFileAudio !== null) {
  deleteFileAudio.addEventListener("click", function () {
    fileAudio.value = "";
    locationUploadFileAudio.classList.remove("d-none");
    locationShowFileAudio.classList.add("d-none");
  });
}

if (buttonAddSound !== null) {
  buttonAddSound.addEventListener("click", function (e) {
    let nameSound = inputNameSound.value;
    let nameSinger = inputNameSinger.value;
    let noFileAudio = fileAudio.files.length === 0;
    if (nameSound === "") {
      textMessageNameSound.classList.remove("d-none");
    }
    if (nameSinger === "") {
      textMessageNameSingerSound.classList.remove("d-none");
    }

    if (noFileAudio === true) {
      textMessageFileAudio.classList.remove("d-none");
    }

    if (nameSound === "" || nameSinger === "" || noFileAudio === true) {
      e.preventDefault();
    }
  });
}

// sử dụng để update lại audio sound trong trang /sound/editor
if (inputFlexSwitchCheckDefault !== null) {
  inputFlexSwitchCheckDefault.addEventListener("click", function () {
    let isSwitchOn = this.checked;

    if (isSwitchOn === true) {
      switchUploadFile.classList.remove("d-none");
    } else {
      switchUploadFile.classList.add("d-none");
      textMessageFileAudio.classList.add("d-none");
    }
  });
}

if (buttonUpdateSound !== null) {
  buttonUpdateSound.addEventListener("click", function (e) {
    let isSwitchOn = inputFlexSwitchCheckDefault.checked;
    let nameSound = inputNameSound.value;
    let nameSinger = inputNameSinger.value;
    let noFileAudio = fileAudio.files.length === 0;
    if (nameSound === "") {
      textMessageNameSound.classList.remove("d-none");
    }
    if (nameSinger === "") {
      textMessageNameSingerSound.classList.remove("d-none");
    }

    if (noFileAudio === true && isSwitchOn === true) {
      textMessageFileAudio.classList.remove("d-none");
    }

    if (
      nameSound === "" ||
      nameSinger === "" ||
      (noFileAudio === true && isSwitchOn === true)
    ) {
      e.preventDefault();
    }
  });
}

// user

const inputUserName = document.getElementById("input_user_name");

const textMessageUserName = document.getElementById("text_message_user_name");

const inputPassword = document.getElementById("input_password");

const textMessagePassword = document.getElementById("text_message_password");

const inputEmail = document.getElementById("input_email");

const textMessageEmail = document.getElementById("text_message_email");

const inputNameUser = document.getElementById("input_name_user");

const textMessageNameUser = document.getElementById("text_message_name_user");

const buttonAddUser = document.getElementById("button_add_user");

const buttonUpdateUser = document.getElementById("button_update_user");

if (buttonAddUser != null) {
  buttonAddUser.addEventListener("click", function (e) {
    let username = inputUserName.value;
    let password = inputPassword.value;
    let email = inputEmail.value;
    let nameuser = inputNameUser.value;
    if (username === "") {
      textMessageUserName.classList.remove("d-none");
    }

    if (password == "") {
      textMessagePassword.classList.remove("d-none");
    }

    if (email === "") {
      textMessageEmail.classList.remove("d-none");
    }

    if (nameuser === "") {
      textMessageNameUser.classList.remove("d-none");
    }

    if (username === "" || password === "" || email === "" || nameuser === "") {
      e.preventDefault();
    }
  });
}

if (buttonUpdateUser != null) {
  buttonUpdateUser.addEventListener("click", function (e) {
    let username = inputUserName.value;
    let password = inputPassword.value;
    let email = inputEmail.value;
    let nameuser = inputNameUser.value;
    if (username === "") {
      textMessageUserName.classList.remove("d-none");
    }

    if (password == "") {
      textMessagePassword.classList.remove("d-none");
    }

    if (email === "") {
      textMessageEmail.classList.remove("d-none");
    }

    if (nameuser === "") {
      textMessageNameUser.classList.remove("d-none");
    }

    if (username === "" || password === "" || email === "" || nameuser === "") {
      e.preventDefault();
    }
  });
}

// USER
