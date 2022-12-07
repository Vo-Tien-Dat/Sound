const showTopBox = document.getElementById("show_top_box");

const backgroundTopBox = document.getElementById("background_top_box");

const formTopBoxAlbum = document.getElementById("form_top_box_album");

const inputTopBoxAlbumName = document.getElementById(
  "input_top_box__album_name"
);

const inputTopBoxSingerName = document.getElementById(
  "input_top_box__singer_name"
);

const buttonTopBoxCreateAlbum = document.getElementById(
  "button_top_box__create_album"
);

const buttonTopBoxCloseAlbum = document.getElementById(
  "button_top_box__close_album"
);

// nhận sử kiện hiển thị TOP_BOX
showTopBox.addEventListener("click", function () {
  backgroundTopBox.classList.remove("d-none");
  formTopBoxAlbum.classList.remove("d-none");
});

// nhận sự kiện đóng TOP_BOX
buttonTopBoxCloseAlbum.addEventListener("click", function (e) {
  backgroundTopBox.classList.add("d-none");
  formTopBoxAlbum.classList.add("d-none");
});

// nhận sự kiện input TOP_BOX
const inputTopBox = formTopBoxAlbum.getElementsByClassName("input_top_box");
let sizeListInputTopBox = inputTopBox.length;

function ActiveButtonTopBoxCreateAlbum() {
  for (let j = 0; j < sizeListInputTopBox; ++j) {
    let value = inputTopBox[j].value;
    if (value === "") {
      return false;
    }
  }
  return true;
}

buttonTopBoxCreateAlbum.disabled = true;

for (let i = 0; i < sizeListInputTopBox; ++i) {
  inputTopBox[i].addEventListener("input", function () {
    let isActiveButton = ActiveButtonTopBoxCreateAlbum();
    buttonTopBoxCreateAlbum.disabled = !isActiveButton;
  });
}

// nhận sự kiện thêm mới TOP_BOX và kiểm tra các ô input ở trong TOP_BOX
buttonTopBoxCreateAlbum.addEventListener("click", function () {
  formTopBoxAlbum.submit();
});

// THÊM MỘT PLAYLIST

const buttonTopBoxCreatePlaylist = document.getElementById(
  "button_top_box__create_playlist"
);

buttonTopBoxCreatePlaylist.addEventListener("click", function(){
  
})