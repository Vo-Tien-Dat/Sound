const beginUpload = document.getElementById("begin-upload");
const pendingUpload = document.getElementById("pending-upload");
const fileSound = document.getElementById("file-sound");
const locationUpload = document.getElementById("location-upload");
const nameSound = document.getElementById("name-sound");
const hidden = "d-none";

function AddElementUpload(files) {
  const htmls = Object.keys(files).map((index) => {
    let nameFile = files[index].name;
    nameFile = nameFile.replace(".jpg", "");
    return `
        <div class="container  form-upload-file-sound">
            <div class="row">
                <div class="col-sm-4"></div>
                <div class="col-sm-8">
                <div class="d-flex flex-column gap-3">
                    <div class="d-block">
                    <div class="d-flex">
                        <p class="text-start mb-0 fs-5 fw-semibold">
                        Title
                        </p>
                    </div>
                    <div class="d-flex ">
                        <input class="d-block px-3 py-1 w-100" id = "name-sound" value = ${nameFile} th:field = "*{nameFile}" />
                    </div>
                    </div>
                    <div class="d-block">
                    <div class="container p-0">
                        <div class="row">
                        <div class="col-sm-6">
                            <div class="d-flex flex-column">
                            <div class="d-block">
                                <p class="text-start fs-5 fw-semibold">Type Sound</p>
                            </div>
                            <div class="d-block">
                                <input class="w-100" />
                            </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="d-flex flex-column">
                            <div class="d-block">
                                <p class="text-start fs-5 fw-semibold">Date</p>
                            </div>
                            <div class="d-block">
                                <input class="w-100" />
                            </div>
                            </div>
                        </div>
                        </div>
                    </div>
                    </div>
                    <div class="d-block"></div>
                </div>
                </div>
            </div>
        </div>
    `;
  });

  locationUpload.innerHTML = htmls.join("");
}

fileSound.addEventListener("change", (event) => {
  const { files } = event.target;
  if (files.length > 0) {
    beginUpload.classList.remove("d-block");
    pendingUpload.classList.add("d-block");
    AddElementUpload(files);
  } else {
    beginUpload.classList.add("d-block");
    pendingUpload.classList.remove("d-block");
  }
});
