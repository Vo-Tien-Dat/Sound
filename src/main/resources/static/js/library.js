const app1 = {
  tab: {
    current: "",
  },
  path: [
    {
      item: "overview",
      id: "library.overview",
    },
    {
      item: "albums",
      id: "library.albums",
    },
    {
      item: "playlists",
      id: "library.playlists",
    },
    {
      item: "sounds",
      id: "library.sounds",
    },
    {
      item: "following",
      id: "library.following",
    },
  ],
  split: function () {
    return window.location.pathname.split("/");
  },
  listen: function (type, name) {
    if (type === null || type === undefined) {
      throw Error("Type is null or undefined");
    }
    if (name === null || name === undefined) {
      throw Error("Name is null or undefined");
    }
    const element = document.getElementById(name);
    element.classList.add("tab-active");
  },
  start: function () {
    const splitUrl = this.split();
    splitUrl.forEach((element) => {
      if (element != "") {
        const result = this.path.find((itemCurrent) => {
          const { item, id } = itemCurrent;
          if (element == item) {
            this.listen("click", id);
          }
        });
      }
    });
  },
};

app1.start();
