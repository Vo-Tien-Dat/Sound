const elementCollapseSidebar = document.getElementById("Sidebar");
const elementSidebarItem =
  elementCollapseSidebar.getElementsByClassName("SidebarItem");

for (let i = 0; i < elementSidebarItem.length; ++i) {
  elementSidebarItem[i].addEventListener("click", function () {
    const elementActive =
      elementCollapseSidebar.getElementsByClassName("SidebarItemActive");
    elementActive[0].classList.remove("SidebarItemActive");
    this.classList.add("SidebarItemActive");
  });
}
