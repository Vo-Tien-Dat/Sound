// chức năng chuyển tab
const app = {
  tab: {
    current: "",
  },
  path: {
    default: ["home", "library"],
  },
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
    const listTab = this.split();
    const position = listTab.length - 1;
    const tabUser = listTab[position];
    try {
      this.listen("click", tabUser);
    } catch (e) {
      console.log(e.message);
    }
  },
};

app.start();
