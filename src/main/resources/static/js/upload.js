// chức năng upload file nhạc
const displayNone = "d-none";
const containerUploadfile = document.getElementById("container-upload-file");
const containerUploadFileDetails = document.getElementById(
  "container-upload-file-details"
);
const inputFileSound = document.getElementById("file-sound");

inputFileSound.addEventListener("change", (event) => {
  const { files } = event.target;
  if (files.length > 0) {
    containerUploadfile.classList.add(displayNone);
    containerUploadFileDetails.classList.remove(displayNone);
    console.log(files);
  }
});
