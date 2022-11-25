// ADMIN

// album

const inputNameAlbum = document.getElementById("input_name_album");

const inputNameSinger = document.getElementById("input_name_singer");

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

// user

// USER
