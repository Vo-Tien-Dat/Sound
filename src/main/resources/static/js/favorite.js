const tabs = document.querySelectorAll("#tab");
const tabPanes = document.querySelectorAll("#tab-pane");

function removeTabActive() {
  let sizeTab = tabs.length;
  for (let i = 0; i < sizeTab; ++i) {
    tabs[i].classList.remove("active");
    tabPanes[i].classList.remove("tab-pane-active");
  }
}

function handleClickTab() {
  let sizeTab = tabs.length;
  for (let i = 0; i < sizeTab; ++i) {
    let tab = tabs[i];
    let tabPane = tabPanes[i];
    tab.addEventListener("click", function (event) {
      removeTabActive();
      this.classList.add("active");
      tabPane.classList.add("tab-pane-active");
    });
  }
}

handleClickTab();
