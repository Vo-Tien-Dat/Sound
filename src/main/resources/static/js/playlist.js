/*
  - xử lí form thêm playlist
*/
const addPlaylist = document.getElementById("add-playlist");
const containerAddPlaylist = document.getElementById("container-add-playlist");
const formAddPlaylist = document.getElementById("form-add-playlist");
const iconCloseFormAddPlaylisst = document.getElementById(
  "icon-close-form-add-playlist"
);
const displayNone = "d-none";
const displayFlex = "d-flex";
addPlaylist.addEventListener("click", () => {
  containerAddPlaylist.classList.remove(displayNone);
  containerAddPlaylist.classList.add(displayFlex);
  console.log("oke");
});

containerAddPlaylist.addEventListener("click", () => {
  containerAddPlaylist.classList.add(displayNone);
  containerAddPlaylist.classList.remove(displayFlex);
});

formAddPlaylist.addEventListener("click", (event) => {
  event.stopPropagation();
});

iconCloseFormAddPlaylisst.addEventListener("click", () => {
  containerAddPlaylist.classList.add(displayNone);
  containerAddPlaylist.classList.remove(displayFlex);
});
