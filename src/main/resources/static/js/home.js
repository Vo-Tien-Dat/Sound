const music = new Audio("vande.mp3");
const songs = [
  // {
  //     id:'1',
  //     songName:` On My Way <br>
  //     <div class="subtitle">Alan Walker</div>`,
  //     poster: "./assets/img/1.jpg"
  // },
];

// Array.from(document.getElementsByClassName('songItem')).forEach((element, i)=>{
//     element.getElementsByTagName('img')[0].src = songs[i].poster;
//     element.getElementsByTagName('h5')[0].innerHTML = songs[i].songName;
// })

let masterPlay = document.getElementById('masterPlay');
let wave = document.getElementsByClassName('wave')[0];

// ** Start and stop sound 
masterPlay.addEventListener('click',()=>{
  
    if (music.paused || music.currentTime <=0) {
        music.play();
        masterPlay.classList.remove('bi-play-fill');
        masterPlay.classList.add('bi-pause-fill');
        wave.classList.add('active2');
    } else {
        music.pause();
        masterPlay.classList.add('bi-play-fill');
        masterPlay.classList.remove('bi-pause-fill');
        wave.classList.remove('active2');
    }
} )


const makeAllPlays = () =>{
    Array.from(document.getElementsByClassName('playListPlay')).forEach((element)=>{
            element.classList.add('bi-play-circle-fill');
            element.classList.remove('bi-pause-circle-fill');
    })
}
const makeAllBackgrounds = () =>{
    Array.from(document.getElementsByClassName('songItem')).forEach((element)=>{
            element.style.background = "rgb(105, 105, 170, 0)";
    })
}

let index;
let poster_master_play = document.getElementById('poster_master_play');
let title = document.getElementById('title');
let imgSound,audioSound,nameSound;
console.log(title )
Array.from(document.getElementsByClassName('playListPlay')).map((element,index)=>{
    element.addEventListener('click', (e)=>{
        imgSound = document.getElementsByClassName('img-play-music')[index]
        audioSound = document.getElementsByClassName('audio-play-music')[index]
        nameSound = document.getElementsByClassName('name-play-music')[index];
        console.log(imgSound,audioSound ,nameSound)
        if(imgSound!=null && audioSound!=null){
          index = e.target.id;
          makeAllPlays();
          e.target.classList.remove('bi-play-circle-fill');
          e.target.classList.add('bi-pause-circle-fill');
          music.src = `assets/audio/data/${audioSound.textContent}`;
          poster_master_play.src =`/assets/img/data/${imgSound.textContent}`;
          music.play();
          // let song_title = songs.filter((ele)=>{
          //     return ele.id == index;
          // })
  
          // song_title.forEach(ele =>{
          //     let {songName} = ele;
          title.innerHTML = nameSound.textContent;
          // })
          masterPlay.classList.remove('bi-play-fill');
          masterPlay.classList.add('bi-pause-fill');
          wave.classList.add('active2');
          music.addEventListener('ended',()=>{
              masterPlay.classList.add('bi-play-fill');
              masterPlay.classList.remove('bi-pause-fill');
              wave.classList.remove('active2');
          })
        }
        // makeAllBackgrounds();
        // Array.from(document.getElementsByClassName('songItem'))[`${index-1}`].style.background = "rgb(105, 105, 170, .1)";
    })
})

let currentStart = document.getElementById("currentStart");
let currentEnd = document.getElementById("currentEnd");
let seek = document.getElementById("seek");
let bar2 = document.getElementById("bar2");
let dot = document.getElementsByClassName("dot")[0];

music.addEventListener("timeupdate", () => {
  let music_curr = music.currentTime;
  let music_dur = music.duration;

  let min = Math.floor(music_dur / 60);
  let sec = Math.floor(music_dur % 60);
  if (sec < 10) {
    sec = `0${sec}`;
  }
  currentEnd.innerText = `${min}:${sec}`;

  let min1 = Math.floor(music_curr / 60);
  let sec1 = Math.floor(music_curr % 60);
  if (sec1 < 10) {
    sec1 = `0${sec1}`;
  }
  currentStart.innerText = `${min1}:${sec1}`;

  let progressbar = parseInt((music.currentTime / music.duration) * 100);
  seek.value = progressbar;
  let seekbar = seek.value;
  bar2.style.width = `${seekbar}%`;
  dot.style.left = `${seekbar}%`;
});

seek.addEventListener("change", () => {
  music.currentTime = (seek.value * music.duration) / 100;
});

music.addEventListener("ended", () => {
  masterPlay.classList.add("bi-play-fill");
  masterPlay.classList.remove("bi-pause-fill");
  wave.classList.remove("active2");
});

let vol_icon = document.getElementById("vol_icon");
let vol = document.getElementById("vol");
let vol_dot = document.getElementById("vol_dot");
let vol_bar = document.getElementsByClassName("vol_bar")[0];

vol.addEventListener("change", () => {
  if (vol.value == 0) {
    vol_icon.classList.remove("bi-volume-down-fill");
    vol_icon.classList.add("bi-volume-mute-fill");
    vol_icon.classList.remove("bi-volume-up-fill");
  }
  if (vol.value > 0) {
    vol_icon.classList.add("bi-volume-down-fill");
    vol_icon.classList.remove("bi-volume-mute-fill");
    vol_icon.classList.remove("bi-volume-up-fill");
  }
  if (vol.value > 50) {
    vol_icon.classList.remove("bi-volume-down-fill");
    vol_icon.classList.remove("bi-volume-mute-fill");
    vol_icon.classList.add("bi-volume-up-fill");
  }

  let vol_a = vol.value;
  vol_bar.style.width = `${vol_a}%`;
  vol_dot.style.left = `${vol_a}%`;
  music.volume = vol_a / 100;
});

let back = document.getElementById("back");
let next = document.getElementById("next");

back.addEventListener("click", () => {
  index -= 1;
  if (index < 1) {
    index = Array.from(document.getElementsByClassName("songItem")).length;
  }
  music.src = `audio/${index}.mp3`;
  poster_master_play.src = `./assets/img/${index}.jpg`;
  music.play();
  let song_title = songs.filter((ele) => {
    return ele.id == index;
  });

  song_title.forEach((ele) => {
    let { songName } = ele;
    title.innerHTML = songName;
  });
  makeAllPlays();

  document.getElementById(`${index}`).classList.remove("bi-play-fill");
  document.getElementById(`${index}`).classList.add("bi-pause-fill");
  makeAllBackgrounds();
  Array.from(document.getElementsByClassName("songItem"))[
    `${index - 1}`
  ].style.background = "rgb(105, 105, 170, .1)";
});
next.addEventListener("click", () => {
  index -= 0;
  index += 1;
  if (index > Array.from(document.getElementsByClassName("songItem")).length) {
    index = 1;
  }
  music.src = `audio/${index}.mp3`;
  poster_master_play.src = `./assets/img/${index}.jpg`;
  music.play();
  let song_title = songs.filter((ele) => {
    return ele.id == index;
  });

  song_title.forEach((ele) => {
    let { songName } = ele;
    title.innerHTML = songName;
  });
  makeAllPlays();

  document.getElementById(`${index}`).classList.remove("bi-play-fill");
  document.getElementById(`${index}`).classList.add("bi-pause-fill");
  makeAllBackgrounds();
  Array.from(document.getElementsByClassName("songItem"))[
    `${index - 1}`
  ].style.background = "rgb(105, 105, 170, .1)";
});

let left_scroll = document.getElementById("left_scroll");
let right_scroll = document.getElementById("right_scroll");
let pop_song = document.getElementsByClassName("pop_song")[0];

if (left_scroll !== null) {
  left_scroll.addEventListener("click", () => {
    pop_song.scrollLeft -= 330;
  });
}
if (right_scroll !== null) {
  right_scroll.addEventListener("click", () => {
    pop_song.scrollLeft += 330;
  });
}

let left_scrolls = document.getElementById("left_scrolls");
let right_scrolls = document.getElementById("right_scrolls");
let item = document.getElementsByClassName("item")[0];

if (left_scrolls != null) {
  left_scrolls.addEventListener("click", () => {
    item.scrollLeft -= 330;
  });
}

if (right_scrolls != null) {
  right_scrolls.addEventListener("click", () => {
    item.scrollLeft += 330;
  });
}

const classNameUser = document.getElementsByClassName("user");
const classNameDropDown = classNameUser[0].getElementsByClassName("dropdown");

if (classNameDropDown[0] !== null && classNameUser[0] !== null) {
  classNameDropDown[0].style.display = "none";

  classNameUser[0].addEventListener("click", function () {
    let isActive = classNameDropDown[0].style.display;
    classNameDropDown[0].style.display = isActive === "none" ? "flex" : "none";
  });
}

document.addEventListener("click", function (e) {
  const currentElement = e.target;
  if (!classNameUser[0].contains(currentElement)) {
    classNameDropDown[0].style.display = "none";
  }
});

const forms = document.querySelectorAll(".needs-validation");

const currentInputPassword = document.getElementById("current_input_password");

const typeInputPassword = document.getElementById("type_input_password");

const retypeInputPassword = document.getElementById("retype_input_password");

const pattern = {
  current_input_password: "",
  type_input_password: "",
  retype_input_password: "",
};

const value = {
  current_input_password: "",
  type_input_password: "",
  retype_input_password: "",
};

const status = {
  current_input_password: "",
  type_input_password: "",
  retype_input_password: "",
};

const reduce = {
  current_input_password: {
    value: "",
    status: false,
    pattern: "",
  },
  type_input_password: {
    value: "",
    status: false,
    pattern: "",
  },
  retype_input_password: {
    value: "",
    status: "",
    pattern: false,
  },
};

const element = {
  current_input_password: "",
  type_input_password: "",
  retype_input_password: "",
};

if (forms !== null) {
  Array.from(forms).forEach((form) => {
    // nghe s??? ki???n thay ?????i c??c input trong form
    const needValidations = document.querySelectorAll(".need-validation");
    Array.from(needValidations).forEach((needValidation) => {
      const formControl = needValidation.querySelector(".form-control");
      const idFormControl = formControl.id;
      const invalidFeedback = needValidation.querySelector(".invalid-feedback");
      element[idFormControl] = needValidation;

      // ch??? d??ng ????? c???p nh???t gi?? tr??
      formControl.addEventListener("input", function (e) {
        reduce[idFormControl].value = this.value;
        if (this.value.length != 0) {
          reduce[idFormControl].status = true;
        } else {
          reduce[idFormControl].status = false;
        }
      });

      //x??? l?? s??? ki???n
      formControl.addEventListener("input", function () {
        if (idFormControl === "type_input_password") {
          const validFeedback =
            element["retype_input_password"].querySelector(".invalid-feedback");
          if (
            reduce["retype_input_password"].value ===
              reduce["type_input_password"].value &&
            reduce["retype_input_password"].value.length === 0
          ) {
            validFeedback.style.display = "none";
          } else {
            validFeedback.style.display = "inline";
          }
        }

        if (idFormControl === "retype_input_password") {
          if (
            reduce["retype_input_password"].value ===
            reduce["type_input_password"].value
          ) {
            invalidFeedback.style.display = "none";
            reduce["retype_input_password"].status = true;
          } else {
            invalidFeedback.style.display = "inline";
            reduce["retype_input_password"].status = false;
          }
        }
      });
    });

    form.addEventListener("submit", function (event) {
      const keys = Object.keys(reduce);
      let activeRequestPost = true;
      Array.from(keys).forEach((key) => {
        if (reduce[key]["status"] == false) {
          activeRequestPost = false;
        }
      });
      if (!activeRequestPost) {
        event.preventDefault();
      }
    });
  });
}

const formNeedEditors = document.querySelectorAll(".form-need-editor");

Array.from(formNeedEditors).forEach((formNeedEditor) => {
  const needEditors = formNeedEditor.querySelectorAll(".need-editor");
  Array.from(needEditors).forEach((needEditor) => {
    const buttonNeedEditor = needEditor.querySelector(".button-need-editor");
    buttonNeedEditor.addEventListener("click", function () {});
  });
});
