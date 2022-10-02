//begin:  show form add playlist
const addPlaylist = document.getElementById("add-playlist");
const containerAddPlaylist = document.getElementById("container-add-playlist");
const formAddPlaylist = document.getElementById("form-add-playlist");
const iconCloseFormAddPlaylisst = document.getElementById(
  "icon-close-form-add-playlist"
);
const unactive = "d-none";
const displayFlex = "d-flex";
addPlaylist.addEventListener("click", () => {
  containerAddPlaylist.classList.remove(unactive);
  containerAddPlaylist.classList.add(displayFlex);
  console.log("oke");
});

containerAddPlaylist.addEventListener("click", () => {
  containerAddPlaylist.classList.add(unactive);
  containerAddPlaylist.classList.remove(displayFlex);
});

formAddPlaylist.addEventListener("click", (event) => {
  event.stopPropagation();
});

iconCloseFormAddPlaylisst.addEventListener("click", () => {
  containerAddPlaylist.classList.add(unactive);
  containerAddPlaylist.classList.remove(displayFlex);
});

//end: show form add playlist


