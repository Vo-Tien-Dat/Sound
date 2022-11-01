// chức năng chọn upload: sound và album sử dụng radio button
const flexRadioDefault1 = document.getElementById("flexRadioDefault1");
const flexRadioDefault2 = document.getElementById("flexRadioDefault2");
// flexRadioDefault1.checked = true;
// flexRadioDefault1.addEventListener("click", () => {
//   console.log(flexRadioDefault1.checked);
//   console.log(flexRadioDefault2.checked);
// });

// flexRadioDefault2.addEventListener("click", () => {
//   console.log(flexRadioDefault1.checked);
//   console.log(flexRadioDefault2.checked);
// });

// chức năng upload file nhạc
const displayNone = "d-none";
const containerUploadfile = document.getElementById("container-upload-file");
const containerUploadFileDetails = document.getElementById(
  "container-upload-file-details"
);
const inputFileSound = document.getElementById("file-sound");

// nhan su kien auto upload file

const autoUploadFile = document.getElementById("file-sound");
const autoUploadFileSecond = document.getElementById("auto-upload-file");
autoUploadFile.onchange = function () {
  document.getElementById("auto-upload-file").submit();
};

// nhận sự kiện upload file
// inputFileSound.addEventListener("change", (event) => {
//   const { files } = event.target;
//   const playlistUpload = document.getElementById("playlist-upload");

//   if (files.length > 0) {
//     const htmls = Object.keys(files).map((index) => {
//       return `
//         <div class="d-block p-0">
//           <div class="container position-relative px-3 py-3 form-upload-file-sound">
//             <div class="d-inline position-absolute" style="top:0%; left:90%">
//               <i class="fa-solid fa-x"></i>
//             </div>
//             <div class="d-flex">
//               <div class="col-sm-4">
//                 <div class="container p-0 ">
//                   <div class="d-flex justify-content-center align-items-center form-upload-image-sound"></div>
//                 </div>
//               </div>
//               <div class="col-sm-8">
//                 <div class="d-flex flex-column gap-3">
//                   <div class="p-0 d-block">
//                     <div class="d-flex flex-column">
//                       <div class="p-0">
//                         <div class="text-start fs-5 fw-semibold">Title</div>
//                       </div>
//                       <div class="p-0 w-100">
//                         <input
//                           class="px-2 py-3 w-100 fs-5 fw-semibold rounded"
//                           type="text"
//                           value="${files[index].name}"
//                         />
//                       </div>
//                     </div>
//                   </div>
//                   <div class="p-0 d-block">
//                     <div class="container p-0">
//                       <div class="row">
//                         <div class="col-6 ">
//                           <div class="d-flex flex-column">
//                             <div class="p-0">
//                               <p class="text-start fs-5 fw-semibold">
//                                 Type sound
//                               </p>
//                             </div>
//                             <div class="p-0 w-100">
//                               <select class="px-1 py-2 w-100 fs-5 fw-semibold">
//                                 <option>dance</option>
//                                 <option>EDM</option>
//                               </select>
//                             </div>
//                           </div>
//                         </div>
//                         <div class="col-6">
//                           <div class="d-flex flex-column">
//                             <div class="p-0">
//                               <p class="text-start fs-5 fw-semibold">
//                                 Release Date
//                               </p>
//                             </div>
//                             <div class="p-0 w-100">
//                               <input
//                                 type="date"
//                                 class="px-2 py-2 w-100 fs-5 fw-semibold"
//                               />
//                             </div>
//                           </div>
//                         </div>
//                       </div>
//                     </div>
//                   </div>
//                 </div>
//               </div>
//             </div>
//           </div>
//         </div>
//         </div>
//       `;
//     });
//     playlistUpload.innerHTML = htmls.join("");
//   }
// });

// const app = {
//   start: function () {},
// };

// app.start();
