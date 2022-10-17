// chức năng chuyển tab
const app = {
  tab: {
    current: "",
  },
  path: [
    {
      item: "home",
      id: "sidebar.home",
    },
    {
      item: "library",
      id: "sidebar.library",
    },
    {
      item: "upload",
      id: "sidebar.upload",
    },
    {
      item: "playlist",
      id: "sidebar.playlist",
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
    element.classList.add("active");
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

app.start();
