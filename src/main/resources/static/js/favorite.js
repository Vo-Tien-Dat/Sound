// const tabs = document.querySelectorAll("#tab");
// const tabPanes = document.querySelectorAll("#tab-pane");

// const tabPanelItem = document.querySelectorAll(".tab-panel-item");

// function removeTabActive() {
//   let sizeTab = tabs.length;
//   for (let i = 0; i < sizeTab; ++i) {
//     tabs[i].classList.remove("active");
//     tabPanes[i].classList.remove("tab-pane-active");
//   }
// }

// function handleClickTab() {
//   let sizeTab = tabs.length;
//   for (let i = 0; i < sizeTab; ++i) {
//     let tab = tabs[i];
//     let tabPane = tabPanes[i];
//     tab.addEventListener("click", function (event) {
//       console.log(tab.dataset.panel);
//       removeTabActive();
//       this.classList.add("active");
//       console.log(tabPaneItem);
//       tabPane.classList.add("tab-pane-active");
//     });
//   }
// }

// handleClickTab();

function removePanelActive(tab, activeClassName) {
  tab.forEach(function (currentValue) {
    console.log(currentValue.classList);
    currentValue.classList.remove(activeClassName);
  });
}

function addPanelActive(tab, index, activeClassName) {
  removePanelActive(tab, activeClassName);
  tab[index].classList.add(activeClassName);
}

function handleClickTab(tabControl, tabPanel) {
  tabControl.forEach(function (currentValue, index) {
    currentValue.addEventListener("click", function () {
      addPanelActive(tabControl, index, "active");
      addPanelActive(tabPanel, index, "tab-panel-item-active");
    });
  });
}

const tabForm = document.querySelectorAll(".tab-form");
tabForm.forEach(function (currentValue) {
  const tabControl = currentValue.querySelector(".tab-control");
  const navItems = tabControl.querySelectorAll(".nav-link");

  const tabPanel = currentValue.querySelector(".tab-panel");
  const tabPanelItems = tabPanel.querySelectorAll(".tab-panel-item");
  handleClickTab(navItems, tabPanelItems);
});
