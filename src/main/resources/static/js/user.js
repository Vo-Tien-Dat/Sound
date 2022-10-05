// chức năng làm active tab

const activeTab = ["tracks", "albums"];
const activeTracks = document.getElementById("tracks");
const activeAlbums = document.getElementById("albums");
const active = "active";

const pathTracks = "/user/tracks";
const pathAlbums = "/user/albums";

function ActivePage(active, path) {
  window.history.pushState({ active: active }, null, path);
}

function RemoveActivePage() {
  const { active } = window.history.state;
  console.log(active);
  activeTab.map((item, index) => {
    if (active != item) {
      document.getElementById(item).classList.remove(active);
    } else {
      document.getElementById(item).classList.add(active);
    }
    console.log("oke");
  });
}

activeTracks.addEventListener("click", () => {
  RemoveActivePage();
  ActivePage("tracks", pathTracks);
});

activeAlbums.addEventListener("click", () => {
  RemoveActivePage();
  ActivePage("albums", pathAlbums);
});
